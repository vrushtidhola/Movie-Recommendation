import React, { useState } from "react";
import AuthCard from "../components/AuthCard";
import LoginForm from "../components/LoginForm";
import SignupForm from "../components/SignupForm";

const AuthPage = () => {
  const [isLogin, setIsLogin] = useState(true);
    console.log({ AuthCard, LoginForm, SignupForm });

  return (
    <AuthCard title={isLogin ? "Welcome Back 🎬" : "Join the MovieVerse"}>
      {isLogin ? (
        <LoginForm onSwitch={() => setIsLogin(false)} />
      ) : (
        <SignupForm onSwitch={() => setIsLogin(true)} />
      )}
    </AuthCard>
  );
};

export default AuthPage;
