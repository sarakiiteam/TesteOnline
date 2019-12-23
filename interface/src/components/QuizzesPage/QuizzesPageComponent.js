import React from "react";
import { Card, Pagination, Divider } from "semantic-ui-react";
import QuizCard from "./QuizCardComponent/QuizCardComponent";

import WrapperComponent from "../WrapperComponent/WrapperComponent";

import "./QuizzesPage.css";

const QuizzesPage = () => {
  // const data = fetchQuizzes();

  const data = [
    {
      name: "a",
      difficulty: 1,
      authorName: "authorA",
      description: "descA"
    },
    {
      name: "b",
      difficulty: 2,
      authorName: "authorB",
      description: "descB"
    },
    {
      name: "c",
      difficulty: 3,
      authorName: "authorC",
      description: "descC"
    },
    {
      name: "d",
      difficulty: 4,
      authorName: "authorD",
      description: "descD"
    },
    {
      name: "d",
      difficulty: 5,
      authorName: "authorD",
      description: "descD"
    },
    {
      name: "d",
      difficulty: 6,
      authorName: "authorD",
      description: "descD"
    },
    {
      name: "d",
      difficulty: 7,
      authorName: "authorD",
      description: "descD"
    },
    {
      name: "d",
      difficulty: 8,
      authorName: "authorD",
      description: "descD"
    },
    {
      name: "d",
      difficulty: 9,
      authorName: "authorD",
      description: "descD"
    }
  ];

  return (
    <WrapperComponent>
      <Card.Group itemsPerRow={3} centered stackable className="quizzesStyle">
        {data.map((quiz, index) => (
          <QuizCard
            key={index}
            name={quiz.name}
            difficulty={quiz.difficulty}
            authorName={quiz.authorName}
            description={quiz.description}
          />
        ))}
      </Card.Group>
      <Divider />
      <Pagination defaultActivePage={1} totalPages={3} />
    </WrapperComponent>
  );
};

export default QuizzesPage;
