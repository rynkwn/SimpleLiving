# Compile the build.
~/Downloads/apache-ant-1.10.2/bin/ant

# Clean out RunnableSL
rm -rf ../RunnableSL

# Move all the .class files to ../RunnableSL
cp -r bin/ ../RunnableSL/

# Move our library files over
mkdir ../RunnableSL/lib
cp -r lib/ ../RunnableSL/lib/

# Move the data files to where they're expected to be.
mkdir ../RunnableSL/src
mkdir ../RunnableSL/src/data
cp -r src/data/ ../RunnableSL/src/data

# Create a shell script for running the thing.
touch ../RunnableSL/run.sh
echo 'java -cp .:lib/gson-2.8.2.jar:asciiPanel.jar base/SmallWorld' > ../RunnableSL/run.sh