package dgtic.unam.modulosieta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import dgtic.unam.modulosieta.databinding.ActivityEliminaStudentBinding
import org.json.JSONArray

class activity_elimina_student : AppCompatActivity()
{
    private lateinit var binding: ActivityEliminaStudentBinding
    private lateinit var volleyAPI: VolleyAPI
    private lateinit var url:String
    private val  ipPuerto = "192.168.210.1:8080"



    override fun onCreate( savedInstanceState: Bundle? )
            {
                super.onCreate( savedInstanceState )
                setContentView( R.layout.activity_elimina_student )

                binding = ActivityEliminaStudentBinding.inflate( layoutInflater )
                setContentView( binding.root )

                volleyAPI= VolleyAPI( this )

                binding.restjsondelete.setOnClickListener {
                    studiantDELETE()
                }
            }


    private fun studiantDELETE()
    {
        val urlJSON = "http://" + ipPuerto + "/borrarestudiante/"+binding.searchText.text.toString()
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

            override fun getMethod(): Int {
                return Method.DELETE
            }
        }
        volleyAPI.add( jsonRequest )
    }
}