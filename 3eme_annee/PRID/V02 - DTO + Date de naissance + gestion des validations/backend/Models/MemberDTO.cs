namespace prid_tuto.Models;

public class MemberDTO
{
    public string Pseudo { get; set; } = "";
    public string? FullName { get; set; }
    public DateOnly? BirthDate { get; set; }
}

public class MemberWithPasswordDTO : MemberDTO
{
    public string Password { get; set; } = "";
}