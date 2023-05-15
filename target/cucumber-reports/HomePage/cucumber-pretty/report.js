$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/HomePage/01_NavigateTo_HomePage.feature");
formatter.feature({
  "name": "Home Page Verifications",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "SEmentor Home page verification",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@home_page"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "User navigates to SEmentor HomePage",
  "keyword": "Given "
});
formatter.match({
  "location": "HomePageSteps.user_navigates_to_sementor_homepage()"
});
formatter.result({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "User click on careers page",
  "keyword": "Then "
});
formatter.match({
  "location": "HomePageSteps.user_click_on_careers_page()"
});
formatter.result({
  "status": "passed"
});
formatter.beforestep({
  "status": "passed"
});
formatter.step({
  "name": "User check whether open postion title is displyed",
  "keyword": "Then "
});
formatter.match({
  "location": "HomePageSteps.user_check_whether_open_postion_title_is_displyed()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});