package com.example.addressbook;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import java.util.List;
public class MainActivity extends AppCompatActivity { private DataHelper db;private ViewFlipper vf;  ListView listView=null; @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
        vf=(ViewFlipper)findViewById(R.id.fill);listView=(ListView)findViewById(R.id.lll);
        final EditText e1=(EditText)findViewById(R.id.ed);final EditText e2=(EditText)findViewById(R.id.ed2); Button add=(Button)findViewById(R.id.buttons);
        add.setOnClickListener(v -> { vf.showNext(); vf.showNext(); e1.setText(""); e2.setText(""); });
        Button exit=(Button)findViewById(R.id.bt); exit.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { finish(); }});
      Button back=(Button)findViewById(R.id.bt5); back.setOnClickListener(new View.OnClickListener() { @Override
          public void onClick(View v) { vf.showPrevious(); vf.showPrevious(); }});
      Button delete=(Button)findViewById(R.id.buttons2);
      delete.setOnClickListener(new View.OnClickListener() { @Override
          public void onClick(View v) { db.deleteAll(); fillListView(); }});
      Button save=(Button)findViewById(R.id.bt6);
      save.setOnClickListener(new View.OnClickListener() { @Override
          public void onClick(View v) { if (e1.getText().toString().equals(" ")||e2.getText().toString().equals(" ")){
                 alertbox("Contacts","All fields are mendatory"); return; }
             db.insert(e1.getText().toString(),e2.getText().toString()); alertbox("Contacts","New Contact Save Successfully");
                 vf.showPrevious(); vf.showPrevious(); fillListView(); }});this.db=new DataHelper(this); fillListView();
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              String Data = db.SelectSpecific(((TextView) view).getText().toString()); String[] data=Data.split("@");
                                              String Id=data[0].toString(); String Name=data[1].toString();
                                              String Address=data[2].toString(); TextView t=(TextView)findViewById(R.id.tt2);
                                              t.setText(Id); TextView t1=(TextView)findViewById(R.id.tt3);
                                              t1.setText(Name); TextView t2=(TextView)findViewById(R.id.tt4); t2.setText(Address);vf.showNext(); }}); }
                                              private void fillListView() { List<String> list=db.selectAll();
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list)); }
    protected void alertbox(String title,String message){ AlertDialog.Builder a=new AlertDialog.Builder(MainActivity.this);
        a.setMessage("do you want ot close this app!!!").setCancelable(false).setPositiveButton("yes", new DialogInterface.OnClickListener() { @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() { @Override
            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); } AlertDialog alert = a.create();}); }}
