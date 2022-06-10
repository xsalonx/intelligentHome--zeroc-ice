import sys
import Ice
import Home

import re
import time
import os

if __name__ == '__main__':
    adapterName = "IHAdapter"
    serverPrefix = "IHServer_"
    serverName = "home1"

    print(sys.argv)

    with Ice.initialize(sys.argv) as communicator:
        cmd = ""
        while cmd != "quit":
            try:
                cmd = input("==> ").strip()
                cmdAndParams = re.split(r" +", cmd)
                cmd = ' '.join(cmdAndParams)

                if re.match("set server .+", cmd):
                    serverName = cmdAndParams[2]
                elif re.match("set adapter .+", cmd):
                    adapterName = cmdAndParams[2]
                elif cmd.startswith("term"):
                    objectName = "Thermometer_{}@{}{}.{}".format(cmdAndParams[1], serverPrefix, serverName, adapterName)
                    print(objectName)
                    base = communicator.stringToProxy(objectName)
                    thermometer = Home.ThermometerPrx.checkedCast(base)

                    if re.match("term \\d+ curr", cmd):
                        print(thermometer.getCurrentTemperature())
                    elif re.match("term \\d+ range .+", cmd):
                        if cmdAndParams[3] == "-n":
                            upper = int(time.time() * 1000)
                        else:
                            raise ValueError("other date range than with upper equal to NOW is not implemented")
                        if cmdAndParams[4] == "-m":
                            lower = 60000 * int(cmdAndParams[5])
                        else:
                            raise ValueError("other time unit than minutes is not implemented, use (-m)")

                        res = thermometer.getMeasuresInTimeRange(lower, upper)
                        print(res)
                    elif re.match("term \\d+ stream", cmd):
                        pass
                    else:
                        raise Exception("incorrect command")

                elif cmd.startswith("light"):
                    objectName = "Light_{}@{}{}.{}".format(cmdAndParams[1], serverPrefix, serverName, adapterName)
                    print(objectName)
                    base = communicator.stringToProxy(objectName)
                    light = Home.LightPrx.checkedCast(base)
                    if re.match("light \\d+ curr", cmd):
                        print(light.getBrightness())
                    elif re.match("light \\d+ set \\d+(\.\\d+)?", cmd):
                        target_brightness = float(cmdAndParams[3])
                        light.setBrightness(target_brightness)
                    else:
                        raise Exception("incorrect command")

                elif cmd.startswith("camera"):
                    if re.match("camera \\d+ stream", cmd):
                        objectName = "Camera_{}@{}{}.{}".format(cmdAndParams[1], serverPrefix, serverName, adapterName)
                        print(objectName)
                        base = communicator.stringToProxy(objectName)
                        camera = Home.CameraPrx.checkedCast(base)
                        camera.ice_ping()

                        repetitions = 2

                        print("sending and receiving {} bytes seq of size {} ...".format(repetitions, -1))
                        results = []
                        for i in range(repetitions):
                            p = camera.getStream()
                            res = p  # .wait()
                            print(res)

                        # camera.shutdownStream()
                    elif cmd != "":
                        raise Exception("incorrect command")


                elif re.match("print .+", cmd):
                    if re.match("print target", cmd):
                        print("@{}{}.{}".format(serverPrefix, serverName, adapterName))
                    else:
                        raise Exception("incorrect command")

                elif cmd != "":
                    raise Exception("incorrect command")

            except Exception as e:
                print(e)
                print()
