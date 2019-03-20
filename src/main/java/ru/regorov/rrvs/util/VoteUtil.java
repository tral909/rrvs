package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.Restaurant;
import ru.regorov.rrvs.model.User;
import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    private VoteUtil() {
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getDate());
    }

    public static List<VoteTo> asTo(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }

    public static Vote createFromTo(VoteTo voteTo, LocalDate date, Restaurant restaurant, User user) {
        Vote vote = new Vote(voteTo.getId(), date);
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        return vote;
    }
}