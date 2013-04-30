/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JB
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notes.findAll", query = "SELECT n FROM Notes n"),
    @NamedQuery(name = "Notes.findByNoteId", query = "SELECT n FROM Notes n WHERE n.noteId = :noteId"),
    @NamedQuery(name = "Notes.findByTitle", query = "SELECT n FROM Notes n WHERE n.title = :title"),
    @NamedQuery(name = "Notes.findByText", query = "SELECT n FROM Notes n WHERE n.text = :text"),
    @NamedQuery(name = "Notes.findByDate", query = "SELECT n FROM Notes n WHERE n.date = :date")})
public class Notes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "note_id", nullable = false)
    private Integer noteId;
    @Basic(optional = false)
    @Column(nullable = false, length = 32)
    private String title;
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String text;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users authorId;
    @JoinColumn(name = "room_id", referencedColumnName = "room_id", nullable = false)
    @ManyToOne(optional = false)
    private Rooms roomId;

    public Notes() {
    }

    public Notes(Integer noteId) {
        this.noteId = noteId;
    }

    public Notes(Integer noteId, String title, String text, Date date) {
        this.noteId = noteId;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Users getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Users authorId) {
        this.authorId = authorId;
    }

    public Rooms getRoomId() {
        return roomId;
    }

    public void setRoomId(Rooms roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noteId != null ? noteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        if ((this.noteId == null && other.noteId != null) || (this.noteId != null && !this.noteId.equals(other.noteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "app.entities.Notes[ noteId=" + noteId + " ]";
    }
    
}
