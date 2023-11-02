import React, { useState, useEffect } from "react";
import EventCard2Form from "./EventCardForm.jsx";
import "../../css/EventCardForm.css";

const ManageEvents = () => {
  const [events, setEvents] = useState([]);
  const [editingEventId, setEditingEventId] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8080/api/events")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch events.");
        }
        return res.json();
      })
      .then((data) => {
        setEvents(data);
      })
      .catch((error) => {
        console.error("Error fetching events:", error);
      });
  }, []);

  const deleteEvent = (id) => {
    fetch(`http://localhost:8080/api/events/${id}`, {
      method: "DELETE",
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to delete event.");
        }
        setEvents((prevEvents) =>
          prevEvents.filter((event) => event.eventId !== id)
        );
      })
      .catch((error) => {
        console.error("Error deleting event:", error);
      });
  };

  const startEditingEvent = (id) => {
    setEditingEventId(id);
  };

  const handleEditSubmit = (id, updatedEvent) => {
    const currentEvent = events.find(event => event.eventId === id);
    if (!updatedEvent.user && currentEvent.user) {
        updatedEvent.user = currentEvent.user;
    }

    fetch(`http://localhost:8080/api/events/update/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(updatedEvent),
    })
    .then((res) => {
        if (!res.ok) {
            throw new Error('Failed to update event.');
        }
        return res.json();
    })
    .then((data) => {
        setEvents(prevEvents => prevEvents.map(event => event.eventId === id ? { ...event, ...data } : event));
        setEditingEventId(null);
    })
    .catch((error) => {
        console.error("Error updating event:", error);
    });
}


  return (
    <div className="manage-events-container">
      <div className="manage-events-main-container">
        <div className="manage-events-header">
          <h1>Manage Events</h1>
        </div>
        <div className="manage-events-body">
          {events.map((event) => (
            <div key={event.eventId}>
              {editingEventId === event.eventId ? (
                <EventCard2Form
                  {...event}
                  onSubmit={() => handleEditSubmit(event.eventId, event)}
                  width={380}
                />
              ) : (
                <>
                  <EventCard2Form {...event} onSubmit={() => {}} width={400} />
                  <button onClick={() => deleteEvent(event.eventId)}>
                    Delete
                  </button>
                  <button onClick={() => startEditingEvent(event.eventId)}>
                    Edit
                  </button>
                </>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default ManageEvents;
