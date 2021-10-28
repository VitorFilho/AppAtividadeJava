package plis.com.Projeto_Parear;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button btnConection, btnWifi;
    private static  final int solicita = 1;
    private static  final int solicita_Conexao = 2;
    BluetoothAdapter bluetoothAdapter = null;//Dispositivo adaptador
    BluetoothDevice device = null;
    BluetoothSocket socket = null;//transição  de dados
    boolean conexao = false;
    private static String MAC = null;
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Canal de comunicação

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConection = (Button) findViewById(R.id.btnConection);
        btnWifi = (Button) findViewById(R.id.btnWifi);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui blothooth", Toast.LENGTH_LONG).show();
        } else if (!bluetoothAdapter.isEnabled()) {//Se não estiver ativado
            Intent ativa = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativa, solicita);
        }
        btnConection.setOnClickListener((view) -> {

                if (conexao ) {
                    //desconectar
                    try {
                        socket.close();
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Bluethooth desconectado" + MAC, Toast.LENGTH_LONG).show();
                    }catch (IOException erro){
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro" + erro, Toast.LENGTH_LONG).show();
                    }
                }else {
                    //Conectar
                    Intent abrelista = new Intent(MainActivity.this,App02.class);//Sair do MainActivity para o App02
                    startActivityForResult(abrelista,solicita_Conexao);
                }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {//
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case solicita:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "O blothooth foi ativado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "O blothooth não foi ativado", Toast.LENGTH_LONG).show();
                    finish();//fechar App
                }
                break;
            case solicita_Conexao:
                if (resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(App02.mac);
                    device = bluetoothAdapter.getRemoteDevice(MAC);
                    //Toast.makeText(getApplicationContext(), "MAC FINAL" + MAC, Toast.LENGTH_LONG).show();
                    try {
                        socket = device.createRfcommSocketToServiceRecord(uuid);
                        socket.connect();

                        conexao = true;
                        Toast.makeText(getApplicationContext(), "Voce está conectado com: " + MAC, Toast.LENGTH_LONG).show();

                    }catch (IOException erro){
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Erro" + erro, Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Fallha ao obter o MAC", Toast.LENGTH_LONG).show();
                }
        }
    }

    public void onClickWifi(View view) {
    }
}