package com.searchdm.darkroom.mydb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
DataBase myDb;
EditText nm,ad;
Button ins,vi,upd,del;
ListView lv;
String uid="";
final Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb =new DataBase(this);
        ins=(Button)findViewById(R.id.insert);
        vi=(Button)findViewById(R.id.viewDt);
        upd=(Button)findViewById(R.id.update);
        del=(Button)findViewById(R.id.del);
        nm=(EditText)findViewById(R.id.name);
        ad=(EditText)findViewById(R.id.address);
        ins.setOnClickListener(this);
        vi.setOnClickListener(this);
        del.setOnClickListener(this);
        upd.setOnClickListener(this);
        lv=(ListView)findViewById(R.id.lv);


    }

    @Override
    public void onClick(View v) {
      if(v==ins){
          Boolean res=myDb.insertData(nm.getText().toString(),ad.getText().toString());
          if(res){
              Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
          }
          else {
              Toast.makeText(MainActivity.this,"Insertion Failed",Toast.LENGTH_LONG).show();
          }
      }
      else if(v==vi){
          Cursor res=myDb.getData();
          if(res.getCount()==0){
              //showMessage("Error","Empty Db");
              Toast.makeText(MainActivity.this,"Empty Database",Toast.LENGTH_LONG).show();

          }
          else{
             // StringBuffer buffer=new StringBuffer();
              ArrayList<String>res1=new ArrayList();
              ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,res1);
              while (res.moveToNext()){
                 /* buffer.append("ID: "+res.getString(0));
                  buffer.append("Name: "+res.getString(1));
                  buffer.append("Address: "+res.getString(2));
                  */
                  String rec="ID : "+ res.getString(0)+"\t Name  : "+ res.getString(1)+"\t Address :"+res.getString(2)+"\n";

                  res1.add(rec);

              }
              //showMessage("Data",buffer.toString());
              lv.setAdapter(adapter);
          }
      }
      else if(v==del){
            getMyId("delete");

      }
      else if(v==upd){
          getMyId("update");
      }
    }
    public void getMyId(final String op){

        LayoutInflater li =LayoutInflater.from(context);
        final View promptView=li.inflate(R.layout.id_prompt,null);
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText ed=(EditText)promptView.findViewById(R.id.uid);
                if(op.equals("delete")) {
                    Integer delCount = myDb.delData(ed.getText().toString());
                    if (delCount > 0) {
                        Toast.makeText(MainActivity.this, "" + delCount + "Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Integer updateStatus=myDb.updateData(nm.getText().toString(),ad.getText().toString(),ed.getText().toString());
                    if (updateStatus>0) {
                        Toast.makeText(MainActivity.this, "Record updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();

    }
    /*
    public void showMessage(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        //builder.show();


    }*/
}
