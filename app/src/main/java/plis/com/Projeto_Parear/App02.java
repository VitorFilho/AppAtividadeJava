package plis.com.Projeto_Parear;



import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class App02 extends ListActivity {

    private BluetoothAdapter bluetoothAdapter02 = null;
    static String mac = null;//Responsável por capturar os dados

    List<String> dispositivosPareados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> ArrayBluethooth =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        bluetoothAdapter02 = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> dispositivosPareados = bluetoothAdapter02.getBondedDevices();//Dispositivos pareados
        if(dispositivosPareados.size() > 0){
        //if(pairedDevice.size() > 0){
            for (BluetoothDevice dispositivo : dispositivosPareados){
                String deviceName = dispositivo.getName();
                //dispositivosPareados
                String nomebt = dispositivo.getName();
                String macbt = dispositivo.getAddress();
                String deviceHardwareAddress = dispositivo.getAddress();
                ArrayBluethooth.add(nomebt + "\n" + macbt);
            }
        }
        Toast.makeText(App02.this,dispositivosPareados.toString(),Toast.LENGTH_LONG).show();
        setListAdapter(ArrayBluethooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();
        //Toast.makeText(getApplicationContext(),"Info:" + informacaoGeral,Toast.LENGTH_LONG).show();

        String endMac = informacaoGeral.substring(informacaoGeral.length() - 17);

        //Toast.makeText(getApplicationContext(),"mac:" + endMac,Toast.LENGTH_LONG).show();

        Intent retornaMac = new Intent();
        retornaMac.putExtra(mac,endMac);
        setResult(RESULT_OK, retornaMac);
        finish();

    }
}