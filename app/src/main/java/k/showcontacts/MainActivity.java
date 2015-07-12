package k.showcontacts;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;


public class MainActivity extends ActionBarActivity {
    ;

    public List<Smsinfo> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] PHONE_PROJECTION = new String[]{"Phone.DISPLAY_NAME","Phone.NUMBER"};//correct:PHONE_PROJECTION Phone.NUMBER,Phone.Contact_id,Phone.DISPLAY_NAME
       // Uri uri=Uri.parse("content://com.android.contacts/data/phones");
      //  Uri uri = Uri.parse("content://icc/adn");
        Cursor c = this.managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,PHONE_PROJECTION,null,null,"date desc");
        int nameColumn=c.getColumnIndex("Phone.DISPLAY_NAME");
        int numColumn=c.getColumnIndex("Phone.NUMBER");

        if(c != null){
            while(c.moveToNext()){
                Smsinfo sms = new Smsinfo();
                sms.setName(c.getString(nameColumn));
                sms.setPhnum(c.getString(numColumn));
                infos.add(sms);
            }
        }
        c.close();
        ListView listView = (ListView)findViewById(R.id.listview);
        listView.setAdapter(new SmsListA(this));
    }

   class SmsListA extends BaseAdapter {
       private LayoutInflater layoutInflater;
       private View myview;

       public SmsListA(Context c) {
           layoutInflater = LayoutInflater.from(c);
       }

       @Override
       public int getCount() {
           // TODO Auto-generated method stub
           return infos.size();
       }

       @Override
       public Object getItem(int position) {
           // TODO Auto-generated method stub
           return null;
       }

       @Override
       public long getItemId(int position) {
           // TODO Auto-generated method stub
           return 0;
       }

       @Override
       public View getView(int position, View counertView, ViewGroup parent) {
           if (counertView == null) {
               counertView = layoutInflater.inflate(R.layout.contacts, null);
           }
           TextView Name = (TextView)counertView.findViewById(R.id.Name);
           TextView phnumN = (TextView)counertView.findViewById(R.id.phnum);
           Name.setText(infos.get(position).getName());
           phnumN.setText(infos.get(position).getPhnum());
           return counertView;
       }
   }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
