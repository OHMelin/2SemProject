package gui;

public class Monitor {
private static Monitor monitor;

private Monitor() {
}

//Singleton Pattern
public static Monitor getInstance() {
	if(monitor == null) {
		monitor = new Monitor();
	}
		return monitor;
}

//NotifyAll Method
public synchronized void notifyAllMethod() {
	notifyAll();
}

//Calls the Thread to wait until further notis
public synchronized void waitThread() throws InterruptedException {
	wait();
	
}

}
