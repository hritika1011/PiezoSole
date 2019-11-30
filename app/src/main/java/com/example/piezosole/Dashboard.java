package com.example.piezosole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.OutputStream;



public class Dashboard extends AppCompatActivity {
    private ImageView foot;
    private Button shared, record, capture;
    private int minval = 25;
    private int maxval = 30;
    CardView card, card2;
    private String sharePath = "no";
    private String ImagePath;
    Intent share = new Intent(Intent.ACTION_SEND);
    private BluetoothSocket btSocket;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT=1;
    private String deviceName,hardwareAddress;


    List<String[]> allRows;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        card = findViewById(R.id.card);
        card2 = findViewById(R.id.card2);

        foot = findViewById(R.id.imageView2);
        shared = findViewById(R.id.button2);
        record = findViewById(R.id.button);
        capture = findViewById(R.id.button4);
        //   hello = findViewById(R.id.textView3);
//        record.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent dash = new Intent(Dashboard.this,Plot.class);
//                startActivity(dash);
//                //userLogin();
//            }
//        });

        record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check if all fields are accurate
                //If yes,
                //   CSVReader reader = new CSVReader(new FileReader("data.csv"), ',', '"', 1);
//                try {
//                    InputStream inputStream = getResources().openRawResource(R.raw.data);
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    final CSVReader csvreader = new CSVReader(reader);
//                    //Read all rows at once
//                   allRows = csvreader.readAll();
//                }
//                catch (IOException ex){
//                    throw new RuntimeException("Error in reading CSV file: "+ex);
//                }
//                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                if(bluetoothAdapter!=null) {
//                    if (!bluetoothAdapter.isEnabled()) {
//                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//                    }
//                    if (bluetoothAdapter.isEnabled()) {
//                        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//                        List<String> s = new ArrayList<String>();
//                        for (BluetoothDevice btd : pairedDevices) {
//                            deviceName = btd.getName();
//                            hardwareAddress = btd.getAddress();
//                            if (deviceName.equals("ESP32test")) {
//                                connect(btd);
//
//
//                            }
//                        }
//                    }
//                }
                InputStream inputStream = getResources().openRawResource(R.raw.data);
                CSVFile csvFile = new CSVFile(inputStream);
                List scoreList = csvFile.read();

                for (Object scoreData : scoreList) {
                    // String score;

                    //score = scoreData[i];
                    int iconcolor;
                    String score = (String) scoreData;
                    int val = Integer.parseInt(score);
                    if (val <= minval) {
                        Log.d("val", score);
                        iconcolor = Color.YELLOW;
//                        card.setBackgroundColor(Color.YELLOW);
//                        card2.setBackgroundColor(Color.YELLOW);
                    } else if (val < maxval) {
                        Log.d("val", score);
                        iconcolor = Color.GREEN;
//                        card.setBackgroundColor(Color.GREEN);
//                        card2.setBackgroundColor(Color.GREEN);
                    }
                    //  else iconColor = Color.RED;
                    else {
                        Log.d("val", score);
                        iconcolor = Color.RED;
//                        card.setBackgroundColor(Color.RED);
//                        card2.setBackgroundColor(Color.RED);//iconColor, PorterDuff.Mode.MULTIPLY);
                    }
                    card.setBackgroundColor(iconcolor);
                    card2.setBackgroundColor(iconcolor);
//
//                    // val = Integer.parseInt(score);

                }

            }
        });
        shared.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check if all fields are accurate
                //If yes,
                if (!sharePath.equals("no")) {
                    share(sharePath);
                }

            }

        });
        capture.setOnClickListener(new View.OnClickListener() {
            ConstraintLayout parent = findViewById(R.id.relativeLayout);

            @Override
            public void onClick(View v) {
                takescreenshot();
                //check if all fields are accurate
                //If yes,
//              Drawable  drawable = getResources().getDrawable(R.drawable.images);
//
//                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
//
//                ImagePath = MediaStore.Images.Media.insertImage(
//                        getContentResolver(),
//                        bitmap,
//                        "images",
//                        "images"
//                );
//
//                Uri uri = Uri.parse(ImagePath);
//
//                Toast.makeText(Dashboard.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();
//
            }

        });

    }
//    void connect(BluetoothDevice btd)
//    {
//        try {
//            btSocket = btd.createRfcommSocketToServiceRecord(MY_UUID);
//        }
//        catch(IOException e)
//        {
//            Log.e("Error","Could not make bluetooth socket");
//        }
//        try{
//            btSocket.connect();
//        }catch(IOException e)
//        {
//            try{
//                btSocket.close();
//            }
//            catch(IOException e1)
//            {
//                Log.e("Error","Error in closing btScoket");
//            }
//        }
//    }
  private void takescreenshot(){
      BitmapDrawable draw = (BitmapDrawable) foot.getDrawable();
      Bitmap bitmap = draw.getBitmap();

      FileOutputStream outStream = null;
      File sdCard = Environment.getExternalStorageDirectory();
      File dir = new File(sdCard.getAbsolutePath() + "/Gallery");
      dir.mkdirs();
      String fileName = String.format("%d.jpg", System.currentTimeMillis());
      try{
          File outFile = new File(dir, fileName);
          outStream = new FileOutputStream(outFile);
          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
          try {
              outStream.flush();
              outStream.close();
          }
          catch(IOException e){
              Log.e("val","Error");
          }
      }
      catch(FileNotFoundException e){
          Log.e("val","error");
      }
  }

    private void share(String sharePath) {
//        Uri imgUri = Uri.parse(sharePath);
//        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//        whatsappIntent.setType("text/plain");
//        whatsappIntent.setPackage("com.whatsapp");
//        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//        whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
//        whatsappIntent.setType("image/jpeg");
//        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        try {
//            startActivity(whatsappIntent);
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(getBaseContext(),"Whatsapp have not been installed.",Toast.LENGTH_LONG).show();
//        }
      //  private void shareImage() {
            Intent share = new Intent(Intent.ACTION_SEND);

            // If you want to share a png image only, you can do:
            // setType("image/png"); OR for jpeg: setType("image/jpeg");
            share.setType("image/*");

            // Make sure you put example png image named myImage.png in your
            // directory
            String imagePath = Environment.getExternalStorageDirectory()
                    + "/images.jpeg";

            File imageFileToShare = new File(imagePath);

            Uri uri = Uri.fromFile(imageFileToShare);
            share.putExtra(Intent.EXTRA_STREAM, uri);

            startActivity(Intent.createChooser(share, "Share Image!"));
       // }

    }




//    @Override
//    public void onResume() {
//        super.onResume();
//
//        //Get MAC address from DeviceListActivity via intent
//        Intent intent = getIntent();
//
//        //Get the MAC address from the DeviceListActivty via EXTRA
//        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//
//        //create device and set the MAC address
//        BluetoothDevice device = btAdapter.getRemoteDevice(address);
//
//        try {
//            btSocket = createBluetoothSocket(device);
//        } catch (IOException e) {
//            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
//        }
//        // Establish the Bluetooth socket connection.
//        try {
//            btSocket.connect();
//        } catch (IOException e) {
//            try {
//                btSocket.close();
//            } catch (IOException e2) {
//                //insert code to deal with this
//            }
//        }
//        mConnectedThread = new ConnectedThread(btSocket);
//        mConnectedThread.start();
//
//        //I send a character when resuming.beginning transmission to check device is connected
//        //If it is not an exception will be thrown in the write method and finish() will be called
       //  mConnectedThread.write("x");
 //}

//    @Override
//    public void onPause() {
//        super.onPause();
//        try {
//            //Don't leave Bluetooth sockets open when leaving activity
//            btSocket.close();
//        } catch (IOException e2) {
//            //insert code to deal with this
//        }
//    }
//
//    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
//    private void checkBTState() {
//
//        if (btAdapter == null) {
//            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
//        } else {
//            if(btAdapter.isEnabled()) {
//            } else {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, 1);
//            }
//        }
//    }
//
//
//    //create new class for connect thread
//    private class ConnectedThread extends Thread {
//        private final InputStream mmInStream;
//        private final OutputStream mmOutStream;
//
//        //creation of the connect thread
//        public ConnectedThread(BluetoothSocket socket) {
//            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
//
//            try {
//                //Create I/O streams for connection
//                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
//            } catch (IOException e) {
//            }
//
//            mmInStream = tmpIn;
//            mmOutStream = tmpOut;
//        }
//
//        public void run() {
//            byte[] buffer = new byte[256];
//            int bytes;
//
//            // Keep looping to listen for received messages
//            while (true) {
//                try {
//                    bytes = mmInStream.read(buffer);            //read bytes from input buffer
//                    String readMessage = new String(buffer, 0, bytes);
//                    // Send the obtained bytes to the UI Activity via handler
//                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
//                } catch (IOException e) {
//                    break;
//                }
//            }
//        }
//
//        //write method
//        public void write(String input) {
//            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
//            try {
//                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
//            } catch (IOException e) {
//                //if you cannot write, close the application
//                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
//                finish();
//
//            }
//        }
//    }
}





