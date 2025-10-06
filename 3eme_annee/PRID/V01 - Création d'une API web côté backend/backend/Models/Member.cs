using System.ComponentModel.DataAnnotations;

namespace prid_tuto.Models;

public class Member
{
    [Key]
    public string Pseudo { get; set; } = "";
    [MinLength(3)]
    public string Password { get; set; } = "";
    [MinLength(3)]
    public string? FullName { get; set; }
}
