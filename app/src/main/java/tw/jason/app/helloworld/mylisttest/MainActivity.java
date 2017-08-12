package tw.jason.app.helloworld.mylisttest;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private List<Map<String,String>> data;
    private String[] from = {"title", "content"};
    private int[] to = {R.id.item_title, R.id.item_content};
    private SimpleAdapter adapter;
    private  int removeIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.list);
        initList();
    }

    private void initList(){

        data = new LinkedList<>();
        Map<String,String> d0 = new HashMap<>();
        d0.put(from[0],"Android");
        d0.put(from[1],"Android ......");
        data.add(d0);
        Map<String,String> d1 = new HashMap<>();
        d1.put(from[0],"iOS");
        d1.put(from[1],"iOS ......");
        data.add(d1);

        adapter = new SimpleAdapter(this, data, R.layout.layout_item, from , to);
        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("brad", "LongClick:" + i);
                removeIndex = i;
                showDeleteDialog();
                return true;
            }
        });
    }

    private void showDeleteDialog(){
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warn");
        builder.setMessage("Delete" + data.get(removeIndex).get(from[0])+"?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem();
                removeIndex = -1;
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }
    private void removeItem(){
        data.remove(removeIndex);
        adapter.notifyDataSetChanged();

    }
    public void clearItem(View view){
        data.clear();
        adapter.notifyDataSetChanged();
    }


    public void addItem(View view){
        Map<String,String> d1 = new HashMap<>();
        d1.put(from[0],"New Item");
        d1.put(from[1],"Content ......" + (int)(Math.random()*49+1));
        data.add(d1);

        adapter.notifyDataSetChanged();
    }

}
