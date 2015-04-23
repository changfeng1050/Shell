package com.example.changfeng.shell;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ShellActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);

        Button mButtonShutdown = (Button) findViewById(R.id.button_shutdown);
        mButtonShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shutdown();
            }
        });



        Button mButtonChmod = (Button) findViewById(R.id.button_chmod);
        mButtonChmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String command = "chmod 666 /dev/ttymxc2";
                shellExecCommand(command, "su");
            }
        });

        Button mButtonPing = (Button) findViewById(R.id.button_ping);
        mButtonPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output = "";
                try {
                    output = Shell.exec("ls");
                } catch (Exception e) {

                } finally {

                }
                System.out.println(output);


                String appHome = " /data/data/jackpal.androidterm/app_HOME/";
                String command = "cp /extsd/pt.sh" + appHome;
                shellExecCommand(command, "su");
                command = "chmod 777 " + appHome + "pt.sh";
                shellExecCommand(command, "su");
                command = appHome + "pt.sh";
                shellExecCommand(command, "su");

                Shell.su();
                shellExecCommand("chmod 777 /system/bin", "su");
                shellExecCommand("cp /extsd/pt.sh /system/bin/", "su");
                shellExecCommand("chmod 666 pt.sh", "su");
                shellExecCommand("/system/bin/pt.sh", "su");

            }
        });

        Button mButtonCopyDbFromSdcard = (Button) findViewById(R.id.button_copy_db_sdcard);
        mButtonCopyDbFromSdcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String telephony_db_path = " /data/data/com.android.providers.telephony/databases/telephony.db";
                String command = "cp /extsd/telephony.db" + telephony_db_path;
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mButtonCopyDbFromUdisk = (Button) findViewById(R.id.button_copy_db_usb);
        mButtonCopyDbFromUdisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telephony_db_path = " /data/data/com.android.providers.telephony/databases/telephony.db";
                String command = "cp /udisk/telephony.db" + telephony_db_path;
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mButtonCopyXmlFromUdisk = (Button) findViewById(R.id.button_copy_apns_conf_usb);
        mButtonCopyXmlFromUdisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String command = "mount -o remount -o rw /system";
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }

                String apns_conf_xml_path = " /etc/apns-conf.xml";
                command = "cp /udisk/apns-conf.xml" + apns_conf_xml_path;
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }
                command = "chmod 777 /etc/apns-conf.xml";
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button mButtonCopyXmlFromSdcard = (Button) findViewById(R.id.button_copy_apns_conf_sdcard);
        mButtonCopyXmlFromSdcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String command = "mount -o remount -o rw /system";
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }

                String apns_conf_xml_path = " /etc/apns-conf.xml";
                command = "cp /extsd/apns-conf.xml" + apns_conf_xml_path;
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }
                command = "chmod 777 /etc/apns-conf.xml";
                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mButton_factory_data_reset = (Button) findViewById(R.id.button_factory_data_reset);
        mButton_factory_data_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_PRIVACY_SETTINGS);
                startActivity(intent);
            }
        });

        Button mButton_dmesg = (Button) findViewById(R.id.button_dmesg);
        mButton_dmesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String command = "dmesg | tail -f > /storage/sdcard0/dmesg.txt";

                if (shellExecCommand(command, "su")) {
                    Toast.makeText(getApplicationContext(), command + " success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), command + " fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button mButton_exit = (Button) findViewById(R.id.button_exit);
        mButton_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shell, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private Runnable mRunnableShutdown = new Runnable() {
        @Override
        public void run() {
            shutdown();

        }
    };


    private static final String RTC_DEV = "/sys/class/rtc/rtc0/wakealarm";

    private static boolean delayPowerOn(long delayMillis) {
        if (delayMillis <= 0)
            return false;

        Log.d("#DEBUG#", "###########: Next power on in " +
                (delayMillis / (24 * 60 * 60 * 1000)) + " day," +
                ((delayMillis % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000)) + " hour," +
                ((delayMillis % (60 * 60 * 1000)) / (60 * 1000)) + " min," +
                ((delayMillis % (60 * 1000)) / 1000) + " sec");
        String commands = "echo +" + (delayMillis / 1000) + " > " + RTC_DEV;
        return shellExecCommand(commands, "su");
    }

    private static void _shutdown() {
        Log.d("#DEBUG#", "!!!!!!!!!! shutdown now !!!!!!!!!!");
        String command = "reboot -p";
        shellExecCommand(command, "su");
    }

    public static void shutdown() {
        delayPowerOn(365 * 24 * 3600 * 1000);
        _shutdown();
    }


    public static boolean shellExecCommand(String command, String shell) {
        return shellExecCommand(new String[]{command}, null, shell);
    }

    public static boolean shellExecCommand(String command, File workingDirectory, String shell) {
        return shellExecCommand(new String[]{command}, workingDirectory, shell);
    }

    public static boolean shellExecCommand(String command[], String shell) {
        return shellExecCommand(command, null, shell);
    }

    public static boolean shellExecCommand(String[] command, File workingDirectory, String shell) {
        if (command == null || command.length == 0)
            return false;

        OutputStream out = null;
        InputStream in = null;
        InputStream err = null;

        try {
            if (shell == null || (shell = shell.trim()).length() == 0)
                return false;
            String exit = "exit\n";

            if (workingDirectory == null)
                workingDirectory = new File("/");

            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(shell, null, workingDirectory);

            // ProcessBuilder builder = new ProcessBuilder(command);
            // builder.directory(workingDirectory);
            // builder.redirectErrorStream(true);
            // Process process = builder.start();

            final int INTERVAL = 200; // 200ms
            final int WAIT_TIME = 20 * 60 * 1000; // 20min

            out = process.getOutputStream();
            for (String cmd : command) {
                if (cmd != null && cmd.length() > 0)
                    out.write(cmd.endsWith("\n") ? cmd.getBytes() : (cmd + "\n").getBytes());
            }
            out.write(exit.getBytes());

            StringBuffer inString = new StringBuffer();
            StringBuffer errString = new StringBuffer();

            in = process.getInputStream();
            err = process.getErrorStream();

            int exitValue = -1;

            int pass = 0;
            while (pass <= WAIT_TIME) {
                try {
                    while (in.available() > 0)
                        inString.append((char) in.read());
                    while (err.available() > 0)
                        errString.append((char) err.read());

                    exitValue = -1;
                    exitValue = process.exitValue();
                    break;
                } catch (IllegalThreadStateException itex) {
                    try {
                        Thread.sleep(INTERVAL);
                        pass += INTERVAL;
                    } catch (InterruptedException e) {
                        Log.e("#ERROR#", "execute command error: " + command, e);
                    }
                }
            }

            if (pass > WAIT_TIME)
                process.destroy();

            return (exitValue == 0);
        } catch (IOException e) {
            Log.e("#ERROR#", "execute command failed: " + command + e.getMessage(), e);
        } finally {
            closeStream(out);
            closeStream(in);
            closeStream(err);
        }

        return false;
    }

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
            }
        }
    }
}
