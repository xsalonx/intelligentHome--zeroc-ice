import sys
import Ice
import sliceGen.Home_ice as Home

import re
import time


if __name__ == '__main__':
    adapterName = "IHAdapter"
    serverPrefix = "IHServer_"
    serverName = "home1"

    with Ice.initialize(sys.argv) as communicator:
        base = communicator.stringToProxy("SimplePrinter:default -p 10000")
        cmd = ""

        while cmd != "quit":
            try:
                cmd = input("==> ").strip()
                cmdAndParams = re.split(r" +", cmd)
                cmd = ' '.join(cmdAndParams)

                if re.match(r"set server .+", cmd):
                    serverName = cmdAndParams[2]
                elif re.match(r"set adapter .+", cmd):
                    adapterName = cmdAndParams[2]
                elif cmd.startswith("term"):
                    objectName = "Thermometer_{}@{}{}.{}".format(cmdAndParams[1], serverPrefix, serverName, adapterName)
                    base = communicator.stringToProxy(objectName)
                    thermometer = Home.ThermometerPrx.checkedCast(base)
                    if re.match(r"term \\d+ curr", cmd):
                        print(thermometer.getCurrentTemperature())
                    elif re.match("term \\d+ range .+", cmd):
                        if cmdAndParams[3] == "-n":
                            upper = int(time.time()*1000)
                        else:
                            raise ValueError("other date range than with upper equal to NOW is not implemented")
                        if cmdAndParams[4] == "-m":
                            lower = 60000 * int(cmdAndParams[5])
                        else:
                            raise ValueError("other time unit than minutes is not implemented, use (-m)")

                        res = thermometer.getMeasuresInTimeRange(lower, upper)
                        print(res)
                    elif re.match(r"term \\d+ stream", cmd):
                        pass
                    else:
                        raise Exception("incorrect command")
                else:
                    raise Exception("incorrect command")



            except Exception as e:
                print(e.__class__)
                print(e)
                print()
