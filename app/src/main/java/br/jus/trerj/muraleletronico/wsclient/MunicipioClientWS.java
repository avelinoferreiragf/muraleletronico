package br.jus.trerj.muraleletronico.wsclient;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import br.jus.trerj.muraleletronico.exceptions.TRERJNonPresentableException;
import br.jus.trerj.muraleletronico.filter.PublicacaoFiltro;
import br.jus.trerj.muraleletronico.loaders.JsonLoader;
import br.jus.trerj.muraleletronico.modelo.Municipio;
import cz.msebera.android.httpclient.Header;

/**
 * Created by avelinoferreiragf on 30/08/16.
 */
public class MunicipioClientWS {

    private static final String URL = "municipios.wsmural";

    private JsonLoader<Municipio> loader = new JsonLoader<Municipio>(Municipio.class, new TypeToken<List<Municipio>>(){}.getType());

    public void consultar() {
        RequestParams parametros = new RequestParams();
        MuralEletronicoRestClient.get(URL, parametros, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // TRATAR POSSIVEL ERRO
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray advogadosJson) {
                try {

                    List<Municipio> municipios = MunicipioClientWS.this.loader.carregar(advogadosJson);
                    System.out.println(municipios);
                    PublicacaoFiltro.getInstance().setMunicipiosDisponiveis(municipios);

                } catch (Exception e) {
                    throw new TRERJNonPresentableException(e);
                }
            }
        });
    }

}

