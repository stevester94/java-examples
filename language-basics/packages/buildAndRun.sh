export CLASSPATH=$PWD:`realpath onClassPath`
pushd onClassPath/myPackage
javac *.java
popd

javac *.java && java Main
