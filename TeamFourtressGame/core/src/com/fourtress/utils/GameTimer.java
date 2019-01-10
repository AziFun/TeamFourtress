package com.fourtress.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameTimer {
		
	private static final int TICK_VALUE = 1; 
	private int maxSeconds = 20;//300; // timer countdown start point (default 5 mins)
	
	private int seconds;
	private boolean stopped;
	private boolean timeUp;
	
	//  formatted values
	private long formattedMinutes;
	private long formattedSeconds;
	
	private Timer timer;
	
	public GameTimer() {
		timeUp = false;
		seconds = maxSeconds;
		
		timer = new Timer();
		startTimer();		
	}
	
	class GameTimerTask extends TimerTask {
		
		public void run() {
			//System.out.println(seconds);
			seconds = seconds - TICK_VALUE;

			// convert to minutes for display purposes
			convertToMins(seconds);
			
			if(seconds <= 0) {
				timeUp = true;
				timer.cancel();
			}
		}
	}
	
	public void startTimer() {
		this.stopped = false;
		
		if(seconds == maxSeconds) {
			// Initial Timer start
			timer.schedule(new GameTimerTask(), 0, 1000); // 1000 milliseconds period set (time between successive task executions)
		} else {
			// We have to create a new timer as we have cancelled the initial timer when we paused
			timer = new Timer();
			timer.schedule(new GameTimerTask(), 0, 1000);
		}
		
	}
	
	public void stopTimer() {	
		stopped = true;
		timer.cancel(); 
	}
	
	private void convertToMins(int seconds) {    
		 formattedMinutes = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
		 formattedSeconds = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);
	}
	
	/**
	 * @return the maxSeconds
	 */
	public int maxSeconds() {
		return maxSeconds;
	}
	
	/**
	 * Set the the maxSeconds
	 */
	public void setMaxSeconds(int maxSeconds) {
			this.maxSeconds = maxSeconds;		
	}
		
	/**
	 * @return the TickValue
	 */
	public static int getTickValue() {
		return TICK_VALUE;
	}
	
	/**
	 * @return the minutes on the timer
	 */
	public long getFormattedMinutes() {
		return formattedMinutes;
	}
	
	/**
	 * @return the seconds on the timer
	 */
	public String getFormattedSeconds() {
		String end;
		if (formattedSeconds < 10) {
			end = "0";
		} else {
			end = "";
		}
		return end + formattedSeconds;
	}
	
	public long getSeconds() {
		return formattedSeconds;
	}
	
	/**
	 * @return the boolean value which indicates timer status
	 */
	public boolean getStatus() {
		return stopped;
	}
	
	/**
	 * @return the boolean value which indicates if the player has ran out of time
	 */
	public boolean getTimeUp() {
		return timeUp;
	}
	
	public static void main(String args[]) {
	    new GameTimer();
	}
}