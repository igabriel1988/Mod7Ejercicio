package dgtic.unam.modulosieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.modulosieta.databinding.ActivityBuscaStudentBinding
import dgtic.unam.modulosieta.databinding.ActivityDatosStudentBinding
import org.json.JSONArray

class activity_busca_student : AppCompatActivity()
{
    private lateinit var binding: ActivityBuscaStudentBinding
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url:String
    private val  ipPuerto = "192.168.210.1:8080"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate( savedInstanceState )
        setContentView( R.layout.activity_datos_student )

        supportActionBar?.setDisplayHomeAsUpEnabled( true )
        supportActionBar?.setHomeButtonEnabled( true )

        binding= ActivityBuscaStudentBinding.inflate( layoutInflater )
        setContentView( binding.root )

        volleyAPI= VolleyAPI( this )


        binding.restjson.setOnClickListener {
            studentJSON()
        }

    }


    /////
    private fun studentJSON()
    {
        val urlJSON = "http://" + ipPuerto + "/estudiantesJSON"
        var cadena = ""
        val jsonRequest = object : JsonArrayRequest(
            urlJSON,
            Response.Listener<JSONArray> { response ->
                ( 0 until response.length() ).forEach {
                    val estudiante = response.getJSONObject( it )
                    val materia = estudiante.getJSONArray("materias" )
                    cadena += estudiante.get( "cuenta" ).toString() + "<"
                    ( 0 until materia.length() ).forEach {
                        val datos = materia.getJSONObject( it )
                        cadena += datos.get( "nombre" ).toString() + "**"+datos.get( "creditos" )
                            .toString()+"---"
                    }
                    cadena += "> \n"
                }
                binding.outText.text = cadena
            },
            Response.ErrorListener {
                binding.outText.text = "No se encuentra la informacion, revise su conexion"
            })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers=HashMap<String,String>()
                headers["User-Agent"]="Mozilla/5.0 (Windows NT 6.1)"
                return headers
            }
        }
        volleyAPI.add(jsonRequest)
    }

    override fun onSupportNavigateUp(): Boolean
    {
        onBackPressed()
        return true
    }

}