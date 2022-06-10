import sys
import Ice
import sliceGen.Home

import re

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




            except Exception as e:
                print(e.__class__)
                print(e)
                print()

        if not base:
            raise RuntimeError("Invalid proxy")
