// Copyright © 2016-2017 Shawn Baker using the MIT License.
package ca.frozen.testlibrary;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import ca.frozen.library.classes.LogFile;

public class LogFilesActivity extends AppCompatActivity
{
	private LogFile log;
	private Button file1Button, file2Button;
	private TextView textView;

	//******************************************************************************
	// onCreate
	//******************************************************************************
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// configure the activity
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_files);

		// open the log file
		log = new LogFile(this, "LogFilesActivity", "TestLib");
		log.info("onCreate");

		// get the text view
		textView = (TextView)findViewById(R.id.log_file);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		// get the file buttons
		file1Button = (Button)findViewById(R.id.log_file_1);
		file2Button = (Button)findViewById(R.id.log_file_2);

		// handle the file 1 button
		file1Button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				loadLogFile(log.getFile1(), R.string.no_file_1, file1Button, file2Button);
			}
		});

		// handle the file 2 button
		file2Button.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				loadLogFile(log.getFile2(), R.string.no_file_2, file2Button, file1Button);
			}
		});

		// handle the email button
		Button emailButton = (Button)findViewById(R.id.log_file_email);
		emailButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				emailLogFiles();
			}
		});

		// load log file 1
		loadLogFile(log.getFile1(), R.string.no_file_1, file1Button, file2Button);
	}

	//******************************************************************************
	// loadLogFile
	//******************************************************************************
	private void loadLogFile(File logFile, int no_file_id, Button thisButton, Button otherButton)
	{
		log.info("load log file: " + logFile.getName());
		thisButton.setSelected(true);
		otherButton.setSelected(false);
		if (logFile.exists())
		{
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(logFile));
				StringBuilder builder = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null)
				{
					builder.append(line + "\n");
				}
				reader.close();
				textView.setText(builder.toString());
			}
			catch (Exception ex)
			{
				textView.setText(no_file_id);
			}
		}
		else
		{
			textView.setText(no_file_id);
		}
	}

	//******************************************************************************
	// emailLogFiles
	//******************************************************************************
	private void emailLogFiles()
	{
		// get the email intent
		log.info("email log files");
		Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("text/plain");

		// set the to and subject fields
		intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { getResources().getString(R.string.email_address) } );
		intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " " + getResources().getString(R.string.log_files));

		// attach the log files
		ArrayList<Uri> uris = new ArrayList<Uri>();
		String providerName = getResources().getString(R.string.file_provider);
		uris.add(FileProvider.getUriForFile(this, providerName, log.getFile1()));
		if (log.getFile2().exists())
		{
			uris.add(FileProvider.getUriForFile(this, providerName, log.getFile2()));
		}
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
		intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		// start the activity
		startActivity(intent);
	}
}
