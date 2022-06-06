package com.example.aula26052022;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText enviarMensagem = null;
    EditText id = null;
    CursoService requestCurso = null;
    CursoResponse curso = null;
    List<String> listaNomeCursos;
    ListView lvCursos;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CursoPost cursoPost = new CursoPost();
//        cursoPost.setName("TúlioRibeiro 26/05/2022");

        requestCurso = new RetrofitConfig()
                .criarService();

        enviarMensagem = findViewById(R.id.etMensagem);
        id = findViewById(R.id.etId);
        Button botaoEnviar = findViewById(R.id.btEnviar);

        botaoEnviar.setOnClickListener(view -> {
            String conteudo = enviarMensagem.getText().toString();

            CursoPost novoCurso = new CursoPost();
            novoCurso.setName(conteudo);

            requestCurso.createRequestPost(novoCurso).enqueue(new Callback<CursoResponse>() {
                @Override
                public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                    curso = response.body();
                    Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CursoResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            "Falha na requisição", Toast.LENGTH_SHORT).show();
                }
            });
        } );

//        requestCurso.enqueue(new Callback<Curso>() {
//            @Override
//            public void onResponse(Call<Curso> call, Response<Curso> response) {
//                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<Curso> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),
//                        "Falha na requisição", Toast.LENGTH_SHORT).show();
//            }
//        });

//        try {
//            requestCurso.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        listaNomeCursos = new ArrayList<>();

        lvCursos = findViewById(R.id.lvCursos);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listaNomeCursos);

        lvCursos.setAdapter(adapter);

        alterarCurso();
        deletarCurso();

    }

    private void alterarCurso() {

        Button botaoEditar = findViewById(R.id.btEditar);

        botaoEditar.setOnClickListener(view -> {
            String editarCurso = enviarMensagem.getText().toString();
            int editarId = Integer.parseInt(id.getText().toString());
            CursoPost alterarNome = new CursoPost();
            alterarNome.setName(editarCurso);

            requestCurso.createRequestPut(alterarNome,editarId).enqueue(new Callback<CursoResponse>() {
                @Override
                public void onResponse(Call<CursoResponse> call, Response<CursoResponse> response) {
                    Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CursoResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Falha de comunicação", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void deletarCurso() {
        Button botaoDeletar = findViewById(R.id.btDeletar);

        botaoDeletar.setOnClickListener(view -> {
            int deletarCurso = Integer.parseInt(id.getText().toString());

            requestCurso.deleteRequest(deletarCurso).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    Toast.makeText(getApplicationContext(), "Deletado com sucesso", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Erro não foi deletado", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void getAllCourses(View view) {

        requestCurso.getAllCourses().enqueue(new Callback<List<CursoResponse>>() {
            @Override
            public void onResponse (Call<List<CursoResponse>> call, Response<List<CursoResponse>> response) {
                Toast.makeText(getApplicationContext(), "Sucesso", Toast.LENGTH_SHORT).show();

                List<CursoResponse> cursoLista = response.body();
                for (CursoResponse curso : cursoLista) {
//                    Log.i(">>> Resultado", curso.getId() + " " + curso.getName());
                    listaNomeCursos.add(curso.getId() + " - " + curso.getName());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CursoResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Falha de comunicação", Toast.LENGTH_SHORT).show();
            }
        });

    }

}