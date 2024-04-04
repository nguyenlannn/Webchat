alter table user_channel
    add constraint uc_user_channel unique (user_id, channel_id);