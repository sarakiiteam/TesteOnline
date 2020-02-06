package mocks.repository;

import database.models.User;
import database.store.interfaces.IUserRepository;
import org.springframework.http.HttpStatus;
import utils.exceptions.ErrorMessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockUserRepository  implements IUserRepository {

    private final List<User> userIList = new ArrayList<>();

    public MockUserRepository() {
    }

    @Override
    public Optional<User> getByUsername(final String username) {
        return userIList
                .stream()
                .filter(x -> x.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public void register(final String username, final String password) throws ErrorMessageException {
        final Optional<User> userOptional = getByUsername(username);
        if(userOptional.isPresent()){
            throw new ErrorMessageException("User already exists", HttpStatus.FOUND);
        }
        userIList.add(new User(){{
            setUsername(username);
            setPassword(password);
        }});
    }

    @Override
    public boolean canLogin(final String username, final String password) {
        return userIList
                .stream()
                .anyMatch(x -> x.getUsername().equals(username) && x.getPassword().equals(password));
    }

    @Override
    public void changePassword(final String username, final String newPassword) throws ErrorMessageException {
        final Optional<User> userOptional = getByUsername(username);
        if(!userOptional.isPresent()){
            throw new ErrorMessageException("User does not exist", HttpStatus.NOT_FOUND);
        }

        userOptional.get().setPassword(newPassword);
    }

    @Override
    public void update(final User object) throws ErrorMessageException {

        final Optional<User> userOptional = getByUsername(object.getUsername());
        if(!userOptional.isPresent()){
            throw new ErrorMessageException("User does not exist", HttpStatus.NOT_FOUND);
        }

        final User user = userOptional.get();
        user.setPassword(object.getPassword());
    }

    @Override
    public void delete(final User object) throws ErrorMessageException {
        final Optional<User> userOptional = getByUsername(object.getUsername());
        if(!userOptional.isPresent()){
            throw new ErrorMessageException("User does not exist", HttpStatus.NOT_FOUND);
        }
        userIList.remove(object);
    }

    @Override
    public List<User> getAll() {
        return userIList;
    }
}
