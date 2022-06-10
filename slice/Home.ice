
//#include <Ice/BuiltinSequences.ice>
module Home {
exception HomeStaffStateException {
    string reason;
};

dictionary <long, double> timedSimpleData;

interface Thermometer {
    double getCurrentTemperature() throws HomeStaffStateException;
    timedSimpleData getMeasuresInTimeRange(long t1, long t2) throws HomeStaffStateException;
};

interface Light {
    double setBrightness(double b) throws HomeStaffStateException;
    double getBrightness(double b) throws HomeStaffStateException;
};

interface Camera {
    void getStream() throws HomeStaffStateException;
}


};