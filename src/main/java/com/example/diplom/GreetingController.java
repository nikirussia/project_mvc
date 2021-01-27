package com.example.diplom;

import com.example.diplom.domain.Company;
import com.example.diplom.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

    @Controller
    public class GreetingController {
        @Autowired
        private MessageRepo messageRepo;

        @GetMapping("/greeting")
        public String greeting(
                @RequestParam(name="name", required=false, defaultValue="World") String name,
                Map<String, Object> model
        ) {
            model.put("name", name);
            return "greeting";
        }



        @GetMapping
        public String main(Map<String, Object> model) {
            Iterable<Company> messages = messageRepo.findAll();

            model.put("messages", messages);

            return "fltr";
        }



        @PostMapping
        public String add(@RequestParam String name_pr, @RequestParam String tag,@RequestParam String qr,
                          @RequestParam Integer ideal, @RequestParam Integer real,  Map<String, Object> model) {
            Company company = new Company(name_pr, tag, qr,ideal,real);

            messageRepo.save(company);

            Iterable<Company> messages = messageRepo.findAll();

            model.put("messages", messages);

            return "main";
        }


        @PostMapping("filter")
        public String filter(@RequestParam Integer filter, Map<String, Object> model) {
            Iterable<Company> messages;
                if(filter!=null) {
                    messages = messageRepo.findByReal(filter);
                    model.put("messages", messages);
                }
                return "fltr";
        }


        @PostMapping("check")
        public void check(@RequestParam String check) {
            //check = qr
            if (check != null && !check.isEmpty()) {

                boolean bool = messageRepo.existsByQr(check);

                if(bool)
                {
                    Company company;
                    company = messageRepo.findByQr(check);
                    company.setReal(1);
                    messageRepo.save(company);
                }
                else {

                    Company company = new Company("unknown", "unknown", check,1,1);
                    messageRepo.save(company);
                }
            }

        }

}


