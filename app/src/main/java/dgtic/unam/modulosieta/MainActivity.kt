package dgtic.unam.modulosieta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity :
                AppCompatActivity(),
                NavigationView.OnNavigationItemSelectedListener
{
    private lateinit var drawer : DrawerLayout

    override fun onCreate( savedInstanceState: Bundle? )
        {
            super.onCreate( savedInstanceState )
            setContentView( R.layout.activity_main )
            inicioToolsBar()
        }

    private fun inicioToolsBar()
            {
                val toolbar:Toolbar = findViewById( R.id.toolbar_main )
                setSupportActionBar( toolbar )
                drawer=findViewById( R.id.drawer_layout )
                val toggle=ActionBarDrawerToggle(
                                                     this,
                                                            drawer,
                                                            toolbar,
                                                            R.string.abrir,
                                                            R.string.cerrar )
                drawer.addDrawerListener( toggle )
                toggle.syncState()
                toolbar.setNavigationIcon( R.drawable.unam_32 )
                iniciarNavegacionView()
            }

    private fun iniciarNavegacionView()
            {
                val navegacionView:NavigationView = findViewById( R.id.nav_view )
                navegacionView.setNavigationItemSelectedListener( this )
                val headerView: View = LayoutInflater.from(this ).inflate(
                                                                            R.layout.header_main,
                                                                            navegacionView,
                                                                 false )
                navegacionView.addHeaderView( headerView )
            }

    override fun onNavigationItemSelected( item:MenuItem ) : Boolean
            {
                   when( item.itemId )
                           {
                               R.id.constraint_layout ->{
                                   startActivity( Intent( this, ConstraintActivity::class.java ) )
                               }
                              R.id.nestedscrollview ->{
                                  startActivity( Intent( this, NestedScrollView::class.java ) )
                              }
                              R.id.collapsing ->{
                                  startActivity( Intent( this, CollapsingToolbarLayout::class.java ) )
                              }

                                ////Llamada a video
                                   R.id.video ->{
                                       startActivity( Intent( this, activity_main_video::class.java ) )
                                   }
                                ////Llamada a musica
                                   R.id.musica ->{
                                       startActivity( Intent( this, activity_main_musica::class.java ) )
                                   }
                                   ////Llamada a grabar
                                   R.id.grabar ->{
                                       startActivity( Intent( this, activity_main_grabar::class.java ) )
                                   }
                       ////Agregar estudiante
                       R.id.addStudent ->{
                           startActivity( Intent( this, main_activity_add_student::class.java ) )
                       }
                       ////id estudiante
                       R.id.despliegaStudent ->{
                           startActivity( Intent( this, activity_datos_student::class.java ) )
                       }

                       ////datos estudiantes
                       R.id.findStudent ->{
                           startActivity( Intent( this, activity_busca_student::class.java ) )
                       }
                       ////elimina estudiante
                       R.id.deleteStudent ->{
                           startActivity( Intent( this, activity_elimina_student::class.java ) )
                       }


                           }

                    drawer.closeDrawer( GravityCompat.START )

                    return true
            }
}