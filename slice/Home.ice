
#include <Ice/BuiltinSequences.ice>
module Home {
exception HomeStaffStateException {
    string reason;
};

interface Thermometer {
    double getCurrentTemperature() throws HomeStaffStateException;
};


};