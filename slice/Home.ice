
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
    void setBrightness(double b) throws HomeStaffStateException;
    double getBrightness() throws HomeStaffStateException;
};

sequence<byte> ByteSeq;
const int ByteSeqSize = 500000;

interface Camera {
    ["marshaled-result", "java:buffer"] ByteSeq getStream();
//    void shutdownStream();
}


};