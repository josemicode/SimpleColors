package com.example.simpleapp.userHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private Context contexto;

    private final String SqlCreateUser = "CREATE TABLE Usuarios (nombre TEXT PRIMARY KEY, password TEXT, apodo TEXT, edad INTEGER, localidad TEXT)";
    private final String SqlCreateScore = "CREATE TABLE Puntuacion (nombre TEXT PRIMARY KEY, aciertos INTEGER, errores INTEGER)";

    private final String SqlDropUser = "DROP TABLE IF EXISTS Usuarios";
    private final String SqlDropScore = "DROP TABLE IF EXISTS Puntuacion";

    private SQLiteDatabase DB = null;

    public static final int DB_Version = 5;
    public static final String DB_Name = "DBUserData.db";

    public DBHandler(Context context) {
        super(context, DB_Name, null, DB_Version);
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLite) {
        sqLite.execSQL(SqlCreateUser);
        sqLite.execSQL(SqlCreateScore);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLite, int i, int i1) {
        sqLite.execSQL(SqlDropUser);
        sqLite.execSQL(SqlDropScore);
        sqLite.execSQL(SqlCreateUser);
        sqLite.execSQL(SqlCreateScore);
    }

    public void closeDB() {
        if (DB != null) {
            DB.close();
        }
    }

    public boolean addUser(String Nombre, String Password, String Apodo, int Edad, String Localidad) {
        DB = this.getWritableDatabase();

        boolean cond = false;

        if (DB != null) {
            ContentValues values = new ContentValues();
            ContentValues values1 = new ContentValues();

            try {

                values.put("nombre", Nombre);
                values.put("password", Password);
                values.put("apodo", Apodo);
                values.put("edad", Edad);
                values.put("localidad", Localidad);

                values1.put("nombre", Nombre);
                values1.put("aciertos", 0);
                values1.put("errores", 0);

                long res = DB.insert("Usuarios", null, values);

                if (res==-1) {
                    System.out.println("Fallo de inserción");
                    cond = false;
                }else {
                    DB.insert("Puntuacion", null, values1);

                    System.out.println("El usuario se ha introducido correctamente");
                    cond = true;
                }


            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        return cond;
    }

    public void deleteUserByName(String userName) {
        DB = this.getWritableDatabase();

        if (DB != null) {
            try {

                String sequence = "nombre = ?";
                String[] deleteArgs = {userName};

                DB.delete("Usuarios", sequence, deleteArgs);
                DB.delete("Puntuacion", sequence, deleteArgs);

                System.out.println("El usuario se ha eliminado correctamente");
            }catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public boolean updateUserByName(String userName, String Apodo, int Edad, String Localidad) {
        DB = this.getWritableDatabase();

        boolean cond = false;

        if (DB != null) {
            ContentValues values = new ContentValues();

            try {

                values.put("apodo", Apodo);
                values.put("edad", Edad);
                values.put("localidad", Localidad);

                String sequence = "nombre = ?";
                String[] updateArgs = {userName};

                long res = DB.update("Usuarios", values, sequence, updateArgs);

                if (res==-1) {
                    System.out.println("Fallo de actualización");
                    cond = false;
                }else {
                    System.out.println("El usuario se ha actualizado correctamente");
                    cond = true;
                }

                closeDB();
            }catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return cond;
    }

    public boolean updateScoreByName(String userName, int Aciertos, int Errores) {
        DB = this.getWritableDatabase();

        boolean cond = false;

        if (DB != null) {
            ContentValues values = new ContentValues();

            try {

                values.put("aciertos", Aciertos);
                values.put("errores", Errores);

                String sequence = "nombre = ?";
                String[] updateArgs = {userName};

                long res = DB.update("Puntuacion", values, sequence, updateArgs);

                if (res==-1) {
                    System.out.println("Fallo de actualización");
                    cond = false;
                }else {
                    System.out.println("El usuario se ha actualizado correctamente");
                    cond = true;
                }

                closeDB();
            }catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return cond;
    }

    public List<String> selectUser(String userName) {
        DB = this.getReadableDatabase();

        List<String> res = new ArrayList<>();

        if (DB != null) {

            String[] selectArgs = {userName};
            String[] fields = {"nombre", "password", "apodo", "edad", "localidad"};

            try {

                Cursor cursor = DB.query("Usuarios", fields, "nombre = ?", selectArgs, null, null, null);
                cursor.moveToFirst();

                res.add(cursor.getString(0));
                res.add(cursor.getString(1));
                res.add(cursor.getString(2));
                res.add(cursor.getString(3));
                res.add(cursor.getString(4));

                cursor.close();

            }catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return res;
    }

    public List<String> selectScore(String userName) {
        DB = this.getReadableDatabase();

        List<String> res = new ArrayList<>();

        if (DB != null) {

            String[] selectArgs = {userName};
            String[] fields = {"nombre", "aciertos", "errores"};

            try {

                Cursor cursor = DB.query("Puntuacion", fields, "nombre = ?", selectArgs, null, null, null);
                cursor.moveToFirst();

                res.add(cursor.getString(0));
                res.add(cursor.getString(1));
                res.add(cursor.getString(2));

                cursor.close();

            }catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        return res;
    }
}
