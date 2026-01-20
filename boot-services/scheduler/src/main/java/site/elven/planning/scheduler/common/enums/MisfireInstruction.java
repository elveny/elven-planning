package site.elven.planning.scheduler.common.enums;

public enum MisfireInstruction {
    ignore,
    fire_now,
    next_with_existing_count,
    now_with_existing_count,
    next_with_remaining_count,
    now_with_remaining_count,
    fire_once_now,
    do_nothing;
}
