# Compile the build.
~/Downloads/apache-ant-1.10.2/bin/ant

# Clean out RunnableSL
rm -rf ../runnableSL

# Move all the .class files to ../RunnableSL
cp -r bin/ ../runnableSL/

# Move our library files over
mkdir ../runnableSL/lib
cp -r lib/ ../runnableSL/lib/

# Move the data files to where they're expected to be.
mkdir ../runnableSL/src
mkdir ../runnableSL/src/data
cp -r src/data/ ../runnableSL/src/data

# Create a shell script for running the thing.
touch ../runnableSL/run.sh
echo 'java -cp .:lib/gson-2.8.2.jar:asciiPanel.jar base/Test' > ../runnableSL/run.sh