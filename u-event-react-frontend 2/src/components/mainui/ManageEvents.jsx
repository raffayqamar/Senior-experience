import React, { useState, useEffect } from 'react';
import EventCard2Form from '../mainui/EventCard2Form.jsx';
import '../../css/ManageEvents.css';

const ManageEvents = () => {
    const [events, setEvents] = useState([]);
    const [editingEventId, setEditingEventId] = useState(null); 

    useEffect(() => {
        fetch('http://localhost:8080/api/events')
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Failed to fetch events.');
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
            method: 'DELETE',
        })
        .then((res) => {
            if (!res.ok) {
                throw new Error('Failed to delete event.');
            }
            setEvents(prevEvents => prevEvents.filter(event => event.eventId !== id));
        })
        .catch((error) => {
            console.error("Error deleting event:", error);
        });
    }

    const startEditingEvent = (id) => {
        setEditingEventId(id);
    }

    const handleEditSubmit = (updatedEvent) => {
        fetch(`http://localhost:8080/api/events/${updatedEvent.eventId}`, {
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
            setEvents(prevEvents => prevEvents.map(event => event.eventId === updatedEvent.eventId ? updatedEvent : event));
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
                                <EventCard2Form {...event} onSubmit={handleEditSubmit} />
                            ) : (
                                <>
                                    <EventCard2Form {...event} onSubmit={() => {}} />
                                    <button onClick={() => deleteEvent(event.eventId)}>Delete</button>
                                    <button onClick={() => startEditingEvent(event.eventId)}>Edit</button>
                                </>
                            )}
                        </div>
                    ))}
                </div>
            </div>
        </div>
    )
}

export default ManageEvents;
