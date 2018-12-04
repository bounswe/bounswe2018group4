import Components from "views/Components/Components.jsx";
import LandingPage from "views/LandingPage/LandingPage.jsx";
import ProfilePage from "views/ProfilePage/Profile.js";
import LoginPage from "views/LoginPage/LoginPage.jsx";
import SignUpPage from "views/SignUpPage/SignUpPage.jsx";
import CreateMemoryPage from "views/CreateMemoryPage/CreateMemoryPage.jsx";
import HomePage from "views/HomePage/HomePage.jsx";

var indexRoutes = [
  { path: "/landing-page", name: "LandingPage", component: LandingPage },
  { path: "/profile-page", name: "ProfilePage", component: ProfilePage },
  { path: "/login-page", name: "LoginPage", component: LoginPage },
  { path: "/signup-page", name: "SignUpPage", component: SignUpPage },
  { path: "/creatememory-page", name: "CreateMemoryPage", component: CreateMemoryPage },
  { path: "/components", name: "Components", component: Components },
  { path: "/home-page", name: "HomePage", component: HomePage },
  { path: "/", name: "LoginPage", component: LoginPage },
];

export default indexRoutes;
