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
import java.util.concurrent.TimeUnit;

import static android.os.Environment.getExternalStoragePublicDirectory;


public class Dashboard extends AppCompatActivity {
    private ImageView foot;
    private Button shared, record, capture;
    private  int minval1 = 1;
    private int minval2 = 10;
    private int minval3 = 15;
    private int minval = 25;
    private  int maxval1 = 35;
    private  int maxval2 = 40;
    private int maxval3 = 50;
    private int maxval = 30;
    CardView card, card2;
    private String sharePath = "no";
    private String ImagePath;
    Intent share = new Intent(Intent.ACTION_SEND);
    private BluetoothSocket btSocket;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT=1;
    private String deviceName,hardwareAddress;
   private  File image;


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
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        InputStream inputStream = getResources().openRawResource(R.raw.data);
                        CSVFile csvFile = new CSVFile(inputStream);
                        List scoreList = csvFile.read();

                        for (Object scoreData : scoreList) {
                            // String score;

                            //score = scoreData[i];
                            int iconcolor;
                            String score = (String) scoreData;
                            int val = Integer.parseInt(score);
                            if (val <= minval2 ) {
                                Log.d("val", score);
                                //iconcolor = Color.YELLOW;
                                card.setBackgroundColor(Color.GREEN);
                                card2.setBackgroundColor(Color.GREEN);
                            } else if (val < minval3) {
                                Log.d("val", score);
                                //iconcolor = Color.GREEN;
                                card.setBackgroundColor(Color.parseColor("#c6d97f"));
                                card2.setBackgroundColor(Color.parseColor("#c6d97f"));
                            }
                            //  else iconColor = Color.RED;
                            else if(val< minval){
                                Log.d("val", score);
                                //iconcolor = Color.RED;
                                card.setBackgroundColor(Color.parseColor("#d5e29f"));
                                card2.setBackgroundColor(Color.parseColor("#d5e29f"));//iconColor, PorterDuff.Mode0000.MULTIPLY);
                            }
                            else if(val < maxval){
                                card.setBackgroundColor(Color.parseColor("#FFFF99"));
                                card2.setBackgroundColor(Color.parseColor("#FFFF99"));
                            }
                            else if(val < maxval1){
                                card.setBackgroundColor(Color.YELLOW);
                                card2.setBackgroundColor(Color.YELLOW);
                            }
                            else if(val <maxval2){
                                card.setBackgroundColor(Color.parseColor("#FFDAB9"));
                                card2.setBackgroundColor(Color.parseColor("#FFDAB9"));
                            }
                            else {
                                card.setBackgroundColor(Color.RED);
                                card2.setBackgroundColor(Color.RED);
                            }

                            try {
                                TimeUnit.SECONDS.sleep(2);
                            }catch(InterruptedException ex){
                                Thread.currentThread().interrupt();
                            }
//
//                    // val = Integer.parseInt(score);

                        }
                    }
                };
                Thread newthread = new Thread(runnable);
                newthread.start();

            }
        });
        shared.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //check if all fields are accurate
                //If yes,
                Uri imgUri = Uri.parse(image.getAbsolutePath());
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                whatsappIntent.putExtra(Intent.EXTRA_STREAM, imgUri);
                whatsappIntent.setType("image/jpeg");
                whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //ToastHelper.MakeShortText("Whatsapp have not been installed.");
                }

            }

        });

        capture.setOnClickListener(new View.OnClickListener() {
            //ConstraintLayout parent = findViewById(R.id.relativeLayout);

            @Override
            public void onClick(View v) {
                v = findViewById(R.id.relativeLayout);
                Bitmap bitmap = Bitmap.createBitmap(v.getWidth(),
                v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
//                 Drawable drawable = foot.getDrawable();
//                 Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();
                 final String name = "report";
                 saveImage(bitmap,name);
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

    private void saveImage(Bitmap finalBitmap, String image_name) {

        File root = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_DCIM );
        String fname = "Image-" + image_name+ ".jpg";
        File file = new File(root + "/Screenshots/");
//        File myDir = new File(root + "/myimg");
//        myDir.mkdirs();

        try {
            image = File.createTempFile(
                    fname,  // prefix
                    ".jpg",         // suffix
                    file    // directorye.printStackTrace();
            );
            FileOutputStream out = new FileOutputStream(image);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String fname = "Image-" + image_name+ ".jpg";
//        File file = new File(root + "/DCIM/", fname);
//        if (file.exists()) file.delete();
//        Log.i("LOAD", root + fname);
//        try {
//            FileOutputStream out = new FileOutputStream(file);
//            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
//    public void screenshot(View view) throws IOException {
//        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
//                view.getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        view.draw(canvas);
//        Date now = new Date();
//        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//        String mPath = Environment.getExternalStorageState() + "/" + now + ".jpg";
//        File imageFile = new File(mPath);
//
//        FileOutputStream outputStream = new FileOutputStream(imageFile);
//        int quality = 100;
//        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//        outputStream.flush();
//        outputStream.close();
//        openScreenshot(imageFile);
//        //return bitmap;
//    }
//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//    }
//private void takeScreenshot() {
//    Date now = new Date();
//    android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
//
//    try {
//        // image naming and path  to include sd card  appending name you choose for file
//        String mPath = Environment.getExternalStorageState() + "/" + now + ".jpg";
//
//        // create bitmap screen capture
//        View v1 = getWindow().getDecorView().getRootView();
//       // v1.set .setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(v1.get .getDrawingCache());
//       // v1.setDrawingCacheEnabled(false);
//
//        File imageFile = new File(mPath);
//
//        FileOutputStream outputStream = new FileOutputStream(imageFile);
//        int quality = 100;
//        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//        outputStream.flush();
//        outputStream.close();
//
//        openScreenshot(imageFile);
//    } catch (Throwable e) {
//        // Several error may come out with file handling or DOM
//        e.printStackTrace();
//    }
//}
//    private void openScreenshot(File imageFile) {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_VIEW);
//        Uri uri = Uri.fromFile(imageFile);
//        intent.setDataAndType(uri, "image/*");
//        startActivity(intent);
//    }
//}

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
//  private void takescreenshot(){
//      BitmapDrawable draw = (BitmapDrawable) foot.getDrawable();
//      Bitmap bitmap = draw.getBitmap();
//
//      FileOutputStream outStream = null;
//      File sdCard = Environment.getExternalStorageDirectory();
//      File dir = new File(sdCard.getAbsolutePath() + "/Gallery");
//      dir.mkdirs();
//      String fileName = String.format("%d.jpg", System.currentTimeMillis());
//      try{
//          File outFile = new File(dir, fileName);
//          outStream = new FileOutputStream(outFile);
//          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
//          try {
//              outStream.flush();
//              outStream.close();
//          }
//          catch(IOException e){
//              Log.e("val","Error");
//          }
//      }
//      catch(FileNotFoundException e){
//          Log.e("val","error");
//      }
//  }






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





