// Copyright © 2017 Shawn Baker using the MIT License.
package ca.frozen.library.classes;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class LogFile
{
	// instance variables
	private String tag = null;
	private LogLevel level = LogLevel.Debug;
	private int maxFileSize = 1024 * 1024;
	private File file1, file2, currFile;
	private FileWriter fOut;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//******************************************************************************
	// LogFile
	//******************************************************************************
	public static void LogFile(Context context, String tag, String baseFileName)
	{
		// save the tag
		this.tag = tag;

		// get the path to the files directory
		File dir = new File(context.getFilesDir(), "logs");
		if (!dir.exists())
		{
			dir.mkdirs();
		}

		// get the two log files
		file1 = new File(dir, baseFileName + "1.log");
		long file1Time = file1.exists() ? file1.lastModified() : 0;
		file2 = new File(dir, baseFileName + "2.log");
		long file2Time = file2.exists() ? file2.lastModified() : 0;

		// open or create the current log file
		try
		{
			currFile = (file1Time >= file2Time) ? file1 : file2;
			if (!currFile.exists())
			{
				currFile.createNewFile();
			}
			fOut = new FileWriter(currFile, true);
		}
		catch (FileNotFoundException ex)
		{
			fOut = null;
		}
		catch (IOException ex)
		{
			fOut = null;
		}
	}

	//******************************************************************************
	// LogFile
	//******************************************************************************
	public LogFile(Context context, String baseFileName)
	{
		this(context, null, baseFileName);
	}

	//******************************************************************************
	// getLogLevel
	//******************************************************************************
	public LogLevel getLogLevel()
	{
		return level;
	}

	//******************************************************************************
	// setLogLevel
	//******************************************************************************
	public void setLogLevel(LogLevel level)
	{
		this.level = level;
	}

	//******************************************************************************
	// getFile1
	//******************************************************************************
	public File getFile1()
	{
		return file1;
	}

	//******************************************************************************
	// getFile2
	//******************************************************************************
	public File getFile2()
	{
		return file2;
	}

	//******************************************************************************
	// write
	//******************************************************************************
	public void write(LogLevel level, String message)
	{
		try
		{
			if (fOut != null && level.ordinal() <= this.level.ordinal())
			{
				// switch files if the current one is full
				if (currFile.length() >= maxFileSize)
				{
					fOut.close();
					currFile = (currFile == file1) ? file2 : file1;
					if (currFile.exists())
					{
						currFile.delete();
					}
					currFile.createNewFile();
					fOut = new FileWriter(currFile);
				}

				// write the log message
				String msg = dateFormat.format(Calendar.getInstance().getTime()) + " - ";
				if (tag != null)
				{
					msg += tag + " - ";
				}
				msg += message + "\n";
				fOut.write(msg);
				fOut.flush();
			}
		}
		catch (IOException ex)
		{
		}
	}

	//******************************************************************************
	// debug
	//******************************************************************************
	public void debug(String message)
	{
		write(LogLevel.Debug, message);
	}

	//******************************************************************************
	// info
	//******************************************************************************
	public void info(String message)
	{
		write(LogLevel.Info, message);
	}

	//******************************************************************************
	// warning
	//******************************************************************************
	public void warning(String message)
	{
		write(LogLevel.Warning, message);
	}

	//******************************************************************************
	// error
	//******************************************************************************
	public void error(String message)
	{
		write(LogLevel.Error, message);
	}

	//******************************************************************************
	// fatal
	//******************************************************************************
	public void fatal(String message)
	{
		write(LogLevel.Fatal, message);
	}

	//******************************************************************************
	// LogLevel
	//******************************************************************************
	public enum LogLevel
	{
		Off,
		Fatal,
		Error,
		Warning,
		Info,
		Debug
	}
}
