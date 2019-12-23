import React, { createContext, useState } from "react";
import PropTypes from "prop-types";

const Context = createContext();

const Provider = ({ children }) => {
  const [quizResponse, setQuizResponse] = useState({});
  const [userDetailsFilled, setUserDetailsFilled] = useState(false);

  const [quiz, setQuiz] = useState({
    title: "Quiz1",
    questions: [
      {
        title: "Question1",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question2",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question3",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question4",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question5",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question6",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question7",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question8",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question9",
        answers: ["Answer1", "Answer2", "Answer3"]
      },
      {
        title: "Question10",
        answers: ["Answer1", "Answer2", "Answer3"]
      }
    ]
  });

  return (
    <Context.Provider
      value={{
        // VALUES
        quizResponse,
        userDetailsFilled,
        quiz,

        // METHODS
        setQuizResponse,
        setUserDetailsFilled
      }}
    >
      {children}
    </Context.Provider>
  );
};

Provider.propTypes = {
  children: PropTypes.node
};

export { Context, Provider };
