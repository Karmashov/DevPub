package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.dto.tag.TagResponseFactory;
import com.skillbox.devpub.dto.tag.TagWeightDto;
import com.skillbox.devpub.dto.universal.Dto;
import com.skillbox.devpub.dto.universal.Response;
import com.skillbox.devpub.model.Tag;
import com.skillbox.devpub.model.enumerated.ModerationStatus;
import com.skillbox.devpub.repository.PostRepository;
import com.skillbox.devpub.repository.Tag2PostRepository;
import com.skillbox.devpub.repository.TagRepository;
import com.skillbox.devpub.service.TagService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Tag2PostRepository tag2PostRepository;

    public TagServiceImpl(PostRepository postRepository, TagRepository tagRepository, Tag2PostRepository tag2PostRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
        this.tag2PostRepository = tag2PostRepository;
    }

    @Override
    public Response getTagsWeight(String query) {
        //@TODO рефакторить???? Формула рассчета????
        List<Tag> search;
        if (query != null) {
            search = tagRepository.findAllByNameContains(query);
        } else {
            search = tagRepository.findAll();
        }

        List<Dto> result = new ArrayList<>();
        int tagUseMax = 0;
        for (Tag tag : search) {
            int tagUseCount = tag2PostRepository
                    .findAllByTagAndPostIsActiveAndPostModerationStatusAndPostTimeBefore(tag, true, ModerationStatus.ACCEPTED, LocalDateTime.now()).size();
            if (tagUseMax < tagUseCount) {
                tagUseMax = tagUseCount;
            }
        }
        for (Tag tag : search) {
            int tagUseCount = tag2PostRepository
                    .findAllByTagAndPostIsActiveAndPostModerationStatusAndPostTimeBefore(tag, true, ModerationStatus.ACCEPTED, LocalDateTime.now()).size();
            result.add(new TagWeightDto(tag.getName(), (double) tagUseCount / tagUseMax));
        }

        return TagResponseFactory.tagWeight(result);
    }
}
