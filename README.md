# due-date-calculator
a homework
## decisions I made
### boilerplate
I used Gradle for dependency management. From hindsight, it might be a bit of an overkill, I wasn't sure how much I was going to need it.

### extra safety checks
I realized only later that submission times are always between 9AM and 5PM, but by then I already had an exception class and some testcases implemented. I know they're not necessary, but decided to keep them.

### flexibility
I was contemplating a lot whether to add more flexibility (to deal with the possibility of changing work hours or workdays, etc.), and decided to just go with what's in the description. Unfortunately, by the time I realized I could ask this in an email, it was already after work hours.

### include or exclude 5PM sharp
I decided to exclude 17:00 from worktime. This means that a submission at 9:00 with 8 hours deadline will be due at 9:00 on the next workday.
My reasoning is that otherwise it'd have added too many complications, and anyway, if it were included, a workday would be 8 hours and 1 minutes.

### TDD
I know what TDD is, but I haven't really practiced it before, so I just added tests after a functionality was ready. I thought this wasn't the time for experiments, but I'd be glad to work with it.
