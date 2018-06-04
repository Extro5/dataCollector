package org.extro5.server.controllers;

import com.google.gson.Gson;
import org.extro5.server.dao.*;
import org.extro5.server.entities.*;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class ServerController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExceptionRepository exceptionRepository;

    @Autowired
    ProgramRepository programRepository;

    @Autowired
    SystemInformationRepository systemInformationRepository;

    @Autowired
    DeviceInfoRepository deviceInfoRepository;

    @Autowired
    SelectionEntityRepository selectionEntityRepository;

    @Autowired
    DiagramRepository diagramRepository;

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/add_user", method = RequestMethod.POST)
    public IdData addUser(ModelMap map, @RequestBody String baseValue) throws Exception {

        Gson gson = new Gson();
        BaseRequestObject baseRequestObject = gson.fromJson(baseValue, BaseRequestObject.class);

        String idUser = userRepository.adduser(baseRequestObject.getUser());
        if (!idUser.equals("")) {
            userRepository.addProgramUser(baseRequestObject.getApiKey(), idUser);
            return new IdData(baseRequestObject.getApiKey(), idUser);
        }
        return null;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/exception", method = RequestMethod.POST)
    public String addException(ModelMap map, @RequestBody String value) {

        Gson gson = new Gson();
        DataException dataException = gson.fromJson(value, DataException.class);

        System.out.println(dataException.getIdUser());
        System.out.println(dataException.getExceptionMessage());
        System.out.println(dataException.getExceptionName());

        exceptionRepository.addException(dataException);

        return "success";
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/program_key", method = RequestMethod.GET)
    public String getProgramKey(ModelMap map) {

        String key = getRandomNumber();
        if ((programRepository.getProgramKey(key)).equals("0")) {
            return key;
        } else {
            return null;
        }
    }

    private static String getRandomNumber() {
        String s = "123456789";
        StringBuilder phoneNumber = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            phoneNumber.append(s.charAt(new Random().nextInt(s.length())));
        }
        return phoneNumber.toString();

    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/system", method = RequestMethod.POST)
    public String systemInformation(ModelMap map, @RequestBody String systemValue) {

        Gson gson = new Gson();
        SystemInformation systemInformation = gson.fromJson(systemValue, SystemInformation.class);

        systemInformationRepository.addSystemInfo(systemInformation);

        return "success";
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/device_info", method = RequestMethod.POST)
    public String deviceInfo(ModelMap map, @RequestBody String deviceInfoValue) {

        Gson gson = new Gson();
        DeviceInfo deviceInfo = gson.fromJson(deviceInfoValue, DeviceInfo.class);

        deviceInfoRepository.addDeviceInfo(deviceInfo);

        return "success";
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/selection_entity", method = RequestMethod.POST)
    public IdData newUserAsAboutMe(ModelMap map, @RequestBody String aboutMeUser) throws Exception {

        Gson gson = new Gson();
        AboutUserInfo aboutUserInfo = gson.fromJson(aboutMeUser, AboutUserInfo.class);

        String idUser = selectionEntityRepository.getEntity(aboutUserInfo.getAboutUserInfo());

        if (!idUser.equals("")) {
            userRepository.addProgramUser(aboutUserInfo.getApiKey(), idUser);
            return new IdData(aboutUserInfo.getApiKey(), idUser);
        }
        return null;
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/diagram/{firstValue}/{secondValue}", method = RequestMethod.GET)
    public MyChartData diagram(ModelMap map, @PathVariable String firstValue, @PathVariable String secondValue) {

        PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
        HashMap<String, String> chartMap = new HashMap<>();
        if (firstValue.equals("users") && secondValue.equals("professions")) {
            List<User> userList = diagramRepository.getAllUsersConsideringProfessions();
            List<String> professionList = new ArrayList<>();
            for (User anUserList : userList) {
                if (!professionList.contains(anUserList.getProfession())) {
                    professionList.add(anUserList.getProfession());
                    chartMap.put(anUserList.getProfession(), "1");
                } else {
                    int count = Integer.parseInt(chartMap.get(anUserList.getProfession()));
                    chartMap.replace(anUserList.getProfession(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries(professionList.get(i), Integer.parseInt(chartMap.get(professionList.get(i))));
            }
            return new MyChartData(chartMap, professionList);
        } else if (firstValue.equals("users") && secondValue.equals("gender")) {
            List<User> userList = diagramRepository.getAllUsersConsideringGender();
            List<String> genderList = new ArrayList<>();
            for (User anUserList : userList) {
                if (!genderList.contains(anUserList.getGender())) {
                    genderList.add(anUserList.getGender());
                    chartMap.put(anUserList.getGender(), "1");
                } else {
                    int count = Integer.parseInt(chartMap.get(anUserList.getGender()));
                    chartMap.replace(anUserList.getGender(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries(genderList.get(i), Integer.parseInt(chartMap.get(genderList.get(i))));
            }
            return new MyChartData(chartMap, genderList);
        } else if (firstValue.equals("users") && secondValue.equals("age")) {
            List<User> userList = diagramRepository.getAllUsersConsideringAge();
            List<String> ageList = new ArrayList<>();
            for (User anUserList : userList) {
                if (!ageList.contains(anUserList.getAge())) {
                    ageList.add(anUserList.getAge());
                    chartMap.put(anUserList.getAge(), "1");
                } else {
                    int count = Integer.parseInt(chartMap.get(anUserList.getAge()));
                    chartMap.replace(anUserList.getAge(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries("age is " + ageList.get(i), Integer.parseInt(chartMap.get(ageList.get(i))));
            }
            return new MyChartData(chartMap, ageList);
        } else if (firstValue.equals("users") && secondValue.equals("hobby")) {
            List<User> userList = diagramRepository.getAllUsersConsideringHobby();
            List<String> hobbyList = new ArrayList<>();
            for (User anUserList : userList) {
                if (!hobbyList.contains(anUserList.getHobby())) {
                    if (anUserList.getHobby() == null || anUserList.getHobby().equals("")) {
                        if (hobbyList.contains("not chosen")) {
                            int count = Integer.parseInt(chartMap.get("not chosen"));
                            chartMap.replace("not chosen", String.valueOf(count), String.valueOf(++count));
                        } else {
                            hobbyList.add("not chosen");
                            chartMap.put("not chosen", "1");
                        }
                    } else {
                        hobbyList.add(anUserList.getHobby());
                        chartMap.put(anUserList.getHobby(), "1");
                    }
                } else {
                    int count = Integer.parseInt(chartMap.get(anUserList.getHobby()));
                    chartMap.replace(anUserList.getHobby(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries(hobbyList.get(i), Integer.parseInt(chartMap.get(hobbyList.get(i))));
            }
            return new MyChartData(chartMap, hobbyList);
        } else if (firstValue.equals("device_info") && secondValue.equals("manufacturer")) {
            List<DeviceInfo> deviceInfoList = diagramRepository.getAllDeviceConsideringManufacturer();
            List<String> manufactureList = new ArrayList<>();
            for (DeviceInfo aDeviceInfoList : deviceInfoList) {
                if (!manufactureList.contains(aDeviceInfoList.getManufacturer())) {
                    manufactureList.add(aDeviceInfoList.getManufacturer());
                    chartMap.put(aDeviceInfoList.getManufacturer(), "1");
                } else {
                    int count = Integer.parseInt(chartMap.get(aDeviceInfoList.getManufacturer()));
                    chartMap.replace(aDeviceInfoList.getManufacturer(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries(manufactureList.get(i), Integer.parseInt(chartMap.get(manufactureList.get(i))));
            }
            return new MyChartData(chartMap, manufactureList);
        } else if (firstValue.equals("device_info") && secondValue.equals("model")) {
            List<DeviceInfo> deviceInfoList = diagramRepository.getAllDeviceConsideringModel();
            List<String> modelList = new ArrayList<>();
            for (DeviceInfo aDeviceInfoList : deviceInfoList) {
                if (!modelList.contains(aDeviceInfoList.getModel())) {
                    modelList.add(aDeviceInfoList.getModel());
                    chartMap.put(aDeviceInfoList.getModel(), "1");
                } else {
                    int count = Integer.parseInt(chartMap.get(aDeviceInfoList.getModel()));
                    chartMap.replace(aDeviceInfoList.getModel(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries(modelList.get(i), Integer.parseInt(chartMap.get(modelList.get(i))));
            }
            return new MyChartData(chartMap, modelList);
        } else if (firstValue.equals("device_info") && secondValue.equals("marketName")) {
            List<DeviceInfo> deviceInfoList = diagramRepository.getAllDeviceConsideringMarketName();
            List<String> marketNameList = new ArrayList<>();
            for (DeviceInfo aDeviceInfoList : deviceInfoList) {
                if (!marketNameList.contains(aDeviceInfoList.getMarketName())) {
                    marketNameList.add(aDeviceInfoList.getMarketName());
                    chartMap.put(aDeviceInfoList.getMarketName(), "1");
                } else {
                    int count = Integer.parseInt(chartMap.get(aDeviceInfoList.getMarketName()));
                    chartMap.replace(aDeviceInfoList.getMarketName(), String.valueOf(count), String.valueOf(++count));
                }
            }
            System.out.println("chartMapSize " + chartMap.size());
            for (int i = 0; i < chartMap.size(); i++) {
                chart.addSeries(marketNameList.get(i), Integer.parseInt(chartMap.get(marketNameList.get(i))));
            }
            return new MyChartData(chartMap, marketNameList);
        } else if (firstValue.equals("users") && secondValue.equals("activity_name")) {

            List<ActivityNameDiagram> activityNameDiagrams = diagramRepository.getAllUsersActivitySession();
            return new MyChartData(activityNameDiagrams);
        }
        return null;
    }
}