import sys
import Ice
import sliceGen.Home

if __name__ == '__main__':
    with Ice.initialize(sys.argv) as communicator:
        base = communicator.stringToProxy("SimplePrinter:default -p 10000")
        printer = Demo.PrinterPrx.checkedCast(base)
        if not printer:
            raise RuntimeError("Invalid proxy")

        printer.printString("Hello World!")
    pass
