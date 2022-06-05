
#include <Ice/BuiltinSequences.ice>
module Home {
exception HomeStaffStateException {
    string reason;
};

interface Streamer {
    Ice::ByteSeq stream() throws HomeStaffStateException;
};

interface Thermometer {
    double getCurrentTemperature() throws HomeStaffStateException;
};


};