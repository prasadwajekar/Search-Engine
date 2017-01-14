
all:
	javac *.java
run:
	java checker
runLive: all
	java checker Start
clean:
	rm -rf *.class
