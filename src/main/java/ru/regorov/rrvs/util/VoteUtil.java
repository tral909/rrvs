package ru.regorov.rrvs.util;

import ru.regorov.rrvs.model.Vote;
import ru.regorov.rrvs.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {
    private VoteUtil() {
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getDateTime(), vote.getRestaurant());
    }

    public static List<VoteTo> asTo(List<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }
}