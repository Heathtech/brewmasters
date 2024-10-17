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

# global
opts = None
decoders = []
tick = datetime.datetime.now()
secrets = None


def my_process(data):
    global opts
    global tick

    ev = aiobs.HCI_Event()
    xx = ev.decode(data)

    if decoders:
        for leader, decoder in decoders:
            xx = decoder.decode(ev)
            if xx and datetime.datetime.now() >= tick:
                tick = datetime.datetime.now() + datetime.timedelta(minutes=5)
                heaths_super_secert_url = secrets["server"]["url"]
                # TODO: need to add the time?= to the url
                # Sending data to the database
                r = requests.post(heaths_super_secert_url, json=json.loads(xx))
                print(r)
                # Local logging for safety
                with open("massive_debt.json", "+a") as f:
                    now = datetime.datetime.now()
                    f.write(f"{now},")
                    f.write(json.dumps(xx))
                    f.write("\n")

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
    with open("secrets.toml", "rb") as f:
        secrets = tomllib.load(f)

    # Configured to only use the tilt
    decoders.append(("Tilt", Tilt()))

    with asyncio.Runner() as runner:
        runner.run(amain())


if __name__ == "__main__":
    main()
