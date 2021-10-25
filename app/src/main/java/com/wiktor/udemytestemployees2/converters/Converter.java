package com.wiktor.udemytestemployees2.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.wiktor.udemytestemployees2.pojo.Speciality;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    // Преобразование объекта в строку для сохранения в базе данных
    @TypeConverter
    public String listSpecialityToString(List<Speciality> specialities) {
        // Что происходит внутри:
                    /*JSONArray jsonArray = new JSONArray();
                        for (Speciality speciality : specialities) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("specialty_id", speciality.getSpecialtyId());
                                jsonObject.put("name", speciality.getName());

                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return jsonArray.toString();*/

        //или эту работу выполняет Gson одной строкой
        return new Gson().toJson(specialities);
    }

    // Обратное преобразование строки из базы данных в список обектов:
    @TypeConverter
    public List<Speciality> stringToListOfSpeciality(String specialitiesAsString) {
        Gson gson = new Gson();
        //смысл такой, но при преобразовании нильзя использовать параметризированные типы
        //ArrayList<Speciality> specialities = gson.fromJson(specialitiesAsString, ArrayList<Speciality>.class);
        // Поэтому нужно убрать параметры в угловых скобках. В итоге получаем ArrayList Object-ов
        ArrayList objects = gson.fromJson(specialitiesAsString, ArrayList.class);
        // Преобразуем ArrayList Object-ов в ArrayList<Specialities>
        ArrayList<Speciality> specialities = new ArrayList<>();
        for (Object o : objects) {
            // Вторым параметром нужно передать класс в который нужно преобразовать строку
            specialities.add(gson.fromJson(o.toString(), Speciality.class));
        }
        return specialities;
    }
}
