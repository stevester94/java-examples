export CLASSPATH=$PWD:`realpath ../_employee`
pushd ../_employee
./buildAndRun.sh > /dev/null
popd
javac *.java && java Main
