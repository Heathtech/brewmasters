import sys
import asyncio
import argparse
import re
import json
import aioblescan as aiobs
from aioblescan.plugins import Tilt
import datetime
import requests
import os
import tomllib
import statistics

# global
opts = None
decoders = []
tick = datetime.datetime.now()
secrets = None
buffer = []


def my_process(data):
    global opts
    global tick
    global buffer

    ev = aiobs.HCI_Event()
    xx = ev.decode(data)

    if decoders:
        for leader, decoder in decoders:
            xx = decoder.decode(ev)
            scan_time = datetime.datetime.now()

            if xx:
                payload = json.loads(xx)
                sg = int(payload["minor"])
                buffer.append(sg)

            if xx and scan_time >= tick:
                tick = scan_time + datetime.timedelta(minutes=5)

                # We want to present the average sg over the last (tick - tock) span
                # Assuming the other payload values do not change in a meaningfull way
                # NumPy might be "overkill" (slower) in this instance? It might be better to keep a running average?
                avg_sg = statistics.mean(buffer)
                payload = json.loads(xx)
                payload["minor"] = avg_sg
                buffer.clear()

                # Local logging for safety
                with open(
                    "/home/beer-potato/Documents/beer-potato/brewmasters/massive_debt.json",
                    "+a",
                ) as f:
                    f.write(f"{scan_time},")
                    f.write(json.dumps(payload))
                    f.write("\n")

                heaths_super_secert_url = secrets["server"]["url"]
                heaths_super_secert_url = f"{heaths_super_secert_url}?time={scan_time}"
                # Sending data to the database
                try:
                    # I don't think this is *really* to blame, but... maybe?
                    r = requests.post(heaths_super_secert_url, json=payload)
                    print(scan_time, r, json.dumps(payload))

                    pass
                except (
                    Exception
                ) as wtf_is_going_on_this_is_a_simple_program_dont_fail_me_python_i_love_you_but_youre_making_me_sad:
                    with open(
                        "/home/beer-potato/Documents/beer-potato/brewmasters/log.txt"
                    ) as error_log:
                        error_log.write(
                            wtf_is_going_on_this_is_a_simple_program_dont_fail_me_python_i_love_you_but_youre_making_me_sad
                        )
                        error_log.write("\n")

                break
    else:
        ev.show(0)


async def amain(args=None):
    global opts

    event_loop = asyncio.get_running_loop()

    # First create and configure a raw socket
    mysocket = aiobs.create_bt_socket(0)

    # create a connection with the raw socket
    conn, btctrl = await event_loop._create_connection_transport(
        mysocket, aiobs.BLEScanRequester, None, None
    )
    # Attach your processing
    btctrl.process = my_process
    try:
        while True:
            await asyncio.sleep(3600)
    except KeyboardInterrupt:
        print("keyboard interrupt")
    finally:
        print("closing event loop")
        await btctrl.stop_scan_request()
        command = aiobs.HCI_Cmd_LE_Advertise(enable=False)
        await btctrl.send_command(command)
        conn.close()


def main():
    # read the secrets file to get access to the top secret server that stores our top secret data
    global secrets
    with open(
        "/home/beer-potato/Documents/beer-potato/brewmasters/secrets.toml", "rb"
    ) as f:
        secrets = tomllib.load(f)

    # Configured to only use the tilt
    decoders.append(("Tilt", Tilt()))

    with asyncio.Runner() as runner:
        runner.run(amain())


if __name__ == "__main__":
    main()
