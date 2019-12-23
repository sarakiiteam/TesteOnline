import React from "react";
import { Card, Button } from "semantic-ui-react";
import PropTypes from "prop-types";
import { Link } from "react-router-dom";

const QuizCard = ({ name, difficulty, authorName, description }) => {
  const getQuiz = () => {
    // fetch the specific quiz
  };

  return (
    <Card>
      <Card.Content>
        <Card.Header>{name}</Card.Header>
        <Card.Meta>
          Difficulty: {difficulty} • {authorName}
        </Card.Meta>
        <Card.Description>{description}</Card.Description>
      </Card.Content>
      <Card.Content extra>
        <Link to="/quiz">
          <div className="ui two buttons">
            <Button
              basic
              color="green"
              onClick={() => {
                getQuiz();
              }}
            >
              Solve
            </Button>
          </div>
        </Link>
      </Card.Content>
    </Card>
  );
};

QuizCard.propTypes = {
  name: PropTypes.string.isRequired,
  difficulty: PropTypes.number.isRequired,
  authorName: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired
};

export default QuizCard;
