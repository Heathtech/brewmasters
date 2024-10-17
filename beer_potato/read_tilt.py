import sys
import asyncio
import argparse
import re
import json
import aioblescan as aiobs
from aioblescan.plugins import EddyStone
# from aioblescan.plugins import RuuviWeather
# from aioblescan.plugins import ATCMiThermometer
# from aioblescan.plugins import ThermoBeacon
from aioblescan.plugins import Tilt
import datetime

# global
opts = None
decoders = []
tick = datetime.datetime.now()


def check_mac(val):
    try:
        if re.match("[0-9a-f]{2}([-:])[0-9a-f]{2}(\\1[0-9a-f]{2}){4}$", val.lower()):
            return val.lower()
    except:
        pass
    raise argparse.ArgumentTypeError("%s is not a MAC address" % val)


def my_process(data):
    global opts
    global tick

    ev = aiobs.HCI_Event()
    xx = ev.decode(data)
    if True: #opts.mac:
        goon = False
        mac = ev.retrieve("peer")
        # for x in mac:
        #     if x.val in opts.mac:
        #         goon = True
        #         break
        # if not goon:
        #     return

    # if opts.raw:
    #     print("Raw data: {}".format(ev.raw_data))
    if decoders:
        for leader, decoder in decoders:
            xx = decoder.decode(ev)
            if xx and (datetime.datetime.now() - tick).seconds > 15:
                tick = datetime.datetime.now()
                print(json.dumps(xx))
                print("saving to file")
                with open("massive_debt.json", "+a") as f:
                    now = datetime.datetime.now()
                    f.write(f"{now},")
                    f.write(json.dumps(xx))
                    f.write("\n")
                # print(f"{json.dumps(xx)}")
                break
                # if opts.leader:
                #     print(f"{leader} {json.dumps(xx)}")
                # else:
                #     print(f"{json.dumps(xx)}")
                # break
    else:
        ev.show(0)


async def amain(args=None):
    global opts

    event_loop = asyncio.get_running_loop()

    # First create and configure a raw socket
    # mysocket = aiobs.create_bt_socket(opts.device)
    mysocket = aiobs.create_bt_socket(0)

    # create a connection with the raw socket
    # This used to work but now requires a STREAM socket.
    # fac=event_loop.create_connection(aiobs.BLEScanRequester,sock=mysocket)
    # Thanks to martensjacobs for this fix
    conn, btctrl = await event_loop._create_connection_transport(
        mysocket, aiobs.BLEScanRequester, None, None
    )
    # Attach your processing
    btctrl.process = my_process
    # if opts.advertise:
    # if True:
        # command = aiobs.HCI_Cmd_LE_Advertise(enable=False)
        # await btctrl.send_command(command)
        # command = aiobs.HCI_Cmd_LE_Set_Advertised_Params(
        #     interval_min=opts.advertise, interval_max=opts.advertise
        # )
        # command = aiobs.HCI_Cmd_LE_Set_Advertised_Params(
        #     interval_min=1, interval_max=500
        # )
        # await btctrl.send_command(command)
        # if opts.url:
        #     myeddy = EddyStone(param=opts.url)
        # else:
        #     myeddy = EddyStone()
        # if opts.txpower:
        #     myeddy.power = opts.txpower
        # command = aiobs.HCI_Cmd_LE_Set_Advertised_Msg(msg=myeddy)
        # await btctrl.send_command(command)
        # command = aiobs.HCI_Cmd_LE_Advertise(enable=True)
        # await btctrl.send_command(command)
    # Probe
    # await btctrl.send_scan_request()
    try:
        while True:
            await asyncio.sleep(3600)
    except KeyboardInterrupt:
        print("keyboard interrupt")
    finally:
        print("closing event loop")
        # event_loop.run_until_complete(btctrl.stop_scan_request())
        await btctrl.stop_scan_request()
        command = aiobs.HCI_Cmd_LE_Advertise(enable=False)
        await btctrl.send_command(command)
        conn.close()


def main():
    # Configured to only use the tilt
    decoders.append(("Tilt", Tilt()))
    
    
    with asyncio.Runner() as runner:
        runner.run(amain())
    
    # try:
    #     asyncio.run(amain())
    # except:
    #     pass


if __name__ == "__main__":
    main()
