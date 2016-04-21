if [ ! -d bin ]; then 
	mkdir bin 
fi
find -name \*.java >javaFiles.txt
javac -d bin -cp .:./lib/jetty/\*:./lib\* @javaFiles.txt
rm javaFiles.txt 
